package com.etoc.shiro;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.etoc.bean.LoginUser;
import com.etoc.model.SysResource;
import com.etoc.model.SysRoleResource;
import com.etoc.model.SysUser;
import com.etoc.model.SysUserResource;
import com.etoc.model.SysUserRole;
import com.etoc.service.SysResourceService;
import com.etoc.service.SysRoleService;
import com.etoc.service.SysUserService;
import com.etoc.utils.TokenUtil;
@Component
public class MyShiroRealm extends AuthorizingRealm {
	@Autowired
	private SysUserService userService;
	@Autowired
	private SysResourceService sysResourceService;
	@Autowired
	private SysRoleService sysRoleService;
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	@Autowired
	private TokenUtil tokenUtil;
	@Override
	public String getName() {
		return "myShiroRealm";
	}
	
	 /**
     * 必须重写此方法，不然会报错
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }
    
	/**
	 * 身份认证
	 * 
	 * @prama
	 * @return
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
			throws AuthenticationException {
		 System.out.println("————身份认证方法————");
			JWTToken jwtToken = (JWTToken) authenticationToken;
			// 获取token
			String token = jwtToken.getToken();
			// 从token中获取用户名
			String username = tokenUtil.getUsernameFromToken(token);
	        // 解密获得username，用于和数据库进行对比
	        if (username == null) {
	        	throw new UnknownAccountException();
	          //  throw new AuthenticationException("token认证失败！");
	        }
	        if(tokenUtil.isTokenExpired(token)) {
	        	 throw new AuthenticationException("token已过期！");
	        }
	        SysUser user = checkUserFromRedis(username);
	        if (user == null) {
	            throw new AuthenticationException("该用户不存在！");
	        }
	        if (user.getStatus()=="available") {
	        	throw new LockedAccountException();
            //throw new AuthenticationException("该用户已被封号！");
	        }
	        return new SimpleAuthenticationInfo(token, token, getName());
	}

	/**
	 * 权限认证
	 * 
	 * @prama
	 * @return
	 * @date 2018/12/5 15:40
	 */
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

	public SysUser checkUserFromRedis(String name) {
		SysUser user = (SysUser) redisTemplate.opsForValue().get(name);
		if (user != null) {
			return user;
		} else {
			SysUser loginUser = userService.getByLoginName(name);
			// 设置缓存
			redisTemplate.opsForValue().set(name, loginUser, 60 * 60 * 24 * 7);
			return loginUser;
		}
	}
}
