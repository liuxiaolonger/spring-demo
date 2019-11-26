package com.etoc.shiro;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.etoc.model.SysResource;
import com.etoc.model.SysRoleResource;
import com.etoc.model.SysUser;
import com.etoc.model.SysUserResource;
import com.etoc.model.SysUserRole;
import com.etoc.service.SysResourceService;
import com.etoc.service.SysRoleService;
import com.etoc.service.SysUserService;

/**
 * 登录
 * 
 * @author Administrator
 * @version [版本号, 2018年12月26日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Component
public class LoginRealm extends AuthorizingRealm {

	@Autowired
	private SysUserService userService;
	@Autowired
	private SysResourceService sysResourceService;
	@Autowired
	private SysRoleService sysRoleService;
	
	
	/**
	 * 设置加密方式为MD5,加密次数2次,如果有认证信息有盐的话则采用盐2次加密
	 */
	public LoginRealm() {
		// super();
		// 采用md5算法
		HashedCredentialsMatcher passwordMatcher = new HashedCredentialsMatcher("md5");
		// 循环加密2次
		passwordMatcher.setHashIterations(2);
		// 再将这个加密组件注入到我们的Realm中
		this.setCredentialsMatcher(passwordMatcher);
	}

	@Override
	public boolean supports(AuthenticationToken token) {
		// 表示此Realm只支持UsernamePasswordToken类型
		return token instanceof UsernamePasswordToken;
	}
	/** 认证 */

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		UsernamePasswordToken jwtToken = (UsernamePasswordToken) token;
		String loginName = jwtToken.getUsername();
		// 根据用户名查询数据库得到用户对象
		SysUser user =userService.getByLoginName(loginName);
		// 用户不存在
		if (user == null) {
			throw new UnknownAccountException();
		}
		// 用户被禁用
		if (user.getStatus()=="available") {
			throw new LockedAccountException();
		}
		try {
			if (null == loginName || null == user.getLoginPsw()) {
				return null;
			}
			String salt = user.getSalt(); // 加密因子 -----盐
//			从这里传入的password（这里是从数据库获取的）和token（filter中登录时生成的）中的password做对比，如果相同就允许登录，不相同就抛出异常。
			SimpleAuthenticationInfo authcInfo = new SimpleAuthenticationInfo(loginName, user.getLoginPsw(), ByteSource.Util.bytes(salt), this.getName());
			// 修改用户登录时间
			return authcInfo;
		} catch (Exception e) {
			throw new AuthenticationException(e);
		}

	}
	/** 授权 */

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		// 根据用户名查找角色，请根据需求实现
		String loginName = (String) principals.getPrimaryPrincipal();
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();

		// 根据username查询角色
		Set<String> roleIds = getRoleIds(loginName);
		authorizationInfo.setRoles(roleIds);// 把查询到的rile信息保存到SimpleAuthorizationInfo中

		// 根据username以及该用户所拥有的角色ids查询资源权限字符串
		Set<String> userPermissions = getUserPermission(loginName, roleIds);
		authorizationInfo.setStringPermissions(userPermissions);

		return authorizationInfo;
	}



	/**
	 * 根据loginname查询角色ids
	 * 
	 * @param userName
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public Set<String> getRoleIds(String loginName) {
		SysUser user = userService.getByLoginName(loginName);
		if (user == null) {
			System.err.println("查询登录用户信息失败！");
			throw new RuntimeException("查询登录用户信息失败！");
		}
		// 保存角色id并过滤掉重复的id
		Set<String> roleIds = new HashSet<>();
		// 根据用户id查询角色ids
		List<SysUserRole> userRoles = userService.getRoleIdsByUserId(user.getId());
		if (CollectionUtils.isEmpty(roleIds)) {
			return roleIds;
		}
		for (SysUserRole userRole : userRoles) {
			String roleId = userRole.getRoleId();
			roleIds.add(roleId);
		}
		return roleIds;
	}

	/**
	 * 根据loginname和角色id查询资源权限字符串
	 * 
	 * @param loginName
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public Set<String> getUserPermission(String loginName, Set<String> roleIds) {
		SysUser user = userService.getByLoginName(loginName);
		if (user == null) {
			System.err.println("查询登录用户信息失败！");
			throw new RuntimeException("查询登录用户信息失败！");
		}
		// 保存资源id并过滤掉重复的id
		Set<String> resourceIds = new HashSet<>();
		// 通过角色id查询角色所拥有的资源id
		for (String roleId : roleIds) {
			List<SysRoleResource> lists = sysRoleService.getResourcesIdsById(roleId);
			for (SysRoleResource roleResource : lists) {
				String resourceId = roleResource.getResourceId();
				resourceIds.add(resourceId);
			}
		}
		// 根据用户ID查询资源ids
		List<SysUserResource> userResources = userService.getResourceIdsByUserId(user.getId());
		for (SysUserResource userResource : userResources) {
			String resourceId = userResource.getResourceId();
			resourceIds.add(resourceId);
		}

		// 保存该用户的所有permission权限字符串
		Set<String> userPermissions = new HashSet<>();
		for (String resourceId : resourceIds) {
			SysResource resource = sysResourceService.selectByPrimaryKey(resourceId);
			userPermissions.add(resource.getPermission());
		}
		return userPermissions;
	}
	
}
