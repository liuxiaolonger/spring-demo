package com.etoc.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.etoc.exception.ChannelException;
import com.etoc.mapper.SysUserMapper;
import com.etoc.mapper.SysUserResourceMapper;
import com.etoc.mapper.SysUserRoleMapper;
import com.etoc.model.SysUser;
import com.etoc.model.SysUserResource;
import com.etoc.model.SysUserRole;
import com.etoc.service.SysUserService;
import com.etoc.util.UUIDUtil;
import com.etoc.vo.Checkbox;

/**
 * <用户管理接口
 * 
 * @author longlong
 * @version [版本号, 2018年8月16日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Service
public class SysUserServiceImpl implements SysUserService {

	@Autowired
	private SysUserMapper sysUserMapper;

	@Autowired
	private SysUserRoleMapper sysUserRoleMapper;

	@Autowired
	private SysUserResourceMapper sysUserResourceMapper;

	/** {@inheritDoc} */

	@Override
	public SysUser selectByFullQuery(String userId) {
		SysUser query = new SysUser();
		query.setId(userId);
		SysUser sysUser = sysUserMapper.selectByFullQuery(query);
		return sysUser;
	}

	/** {@inheritDoc} */

	@Override
	public boolean selectByQuery(String loginName) {
		boolean flag = false;
		SysUser query = new SysUser();

		query.setLoginName(loginName);
		int id = sysUserMapper.selectByQuery(query);
		if (id > 0) {
			flag = true;
		}
		return flag;
	}

	/** {@inheritDoc} */

	@Override
	public List<Checkbox> selectUserNamesByIds() {
		List<Checkbox> result = new ArrayList<Checkbox>();
		List<SysUser> loginNames = sysUserMapper.selectUserNamesByIds();
		for (SysUser sysUser : loginNames) {
			Checkbox c = new Checkbox();
			c.setId(sysUser.getId());
			c.setName(sysUser.getUserName());
			result.add(c);
		}
		return result;
	}

	@Override
	public SysUser getByLoginName(String loginName) {

		SysUser user = new SysUser();
		user.setLoginName(loginName);
		SysUser userInfo = sysUserMapper.selectByUserName(user);
		return userInfo;
	}

	/**
	 * 添加用户和角色关联表资源
	 * 
	 * @param userId
	 * @param roleId
	 * @see [类、类#方法、类#成员]
	 */
	public void add(String userId, String jsons) {
		if (StringUtils.isEmpty(userId)) {
			throw new ChannelException("用户id不能为空！");
		}
		JSONArray roleIds = JSONArray.parseArray(jsons);
		for (int i = 0; i < roleIds.size(); i++) {
			JSONObject jsonObject = (JSONObject) roleIds.get(i);
			String roleId = jsonObject.getString("roleId");
			SysUserRole sur = new SysUserRole();
			sur.setId(UUIDUtil.getUUID());
			sur.setUserId(userId);
			sur.setRoleId(roleId);
			sysUserRoleMapper.insert(sur);
		}

	}

	/** {@inheritDoc} */

	@Override
	public List<SysUserRole> getRoleIdsByUserId(String userId) {
		SysUserRole sur = new SysUserRole();
		sur.setUserId(userId);
		List<SysUserRole> sysUserRoles = sysUserRoleMapper.selectByQuery(sur);
		return sysUserRoles;
	}

	/** {@inheritDoc} */

	@Override
	public List<SysUserResource> getResourceIdsByUserId(String userId) {
		SysUserResource userResource = new SysUserResource();
		userResource.setUserId(userId);
		List<SysUserResource> sysUserResources = sysUserResourceMapper.selectBySysResourceModel(userResource);
		return sysUserResources;
	}

	/** {@inheritDoc} */
	 
	@Override
	public void modifyUser(SysUser user) {
		sysUserMapper.updateByPrimaryKeySelective(user);
		
	}

//	/**
//	 * 根据用户ID更改用户的状态 
//	 * 
//	 * @param userIds 多个用户ID
//	 * @param status  状态码
//	 * @see [类、类#方法、类#成员]
//	 */
//	public void updateUserStatus(String userIds, int status) {
//		if (StringUtils.isBlank(userIds)) {
//			throw new HttpMessageConversionException("恢复的用户ID不能为空");
//		}
//		sysUserMapper.updateBatchStatus(userIds, status);
//	}

}
