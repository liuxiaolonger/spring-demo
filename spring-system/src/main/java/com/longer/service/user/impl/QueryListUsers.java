package com.longer.service.user.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.etoc.exception.ChannelException;
import com.longer.base.impl.SysQueryListService;
import com.longer.dao.model.SysUser;
import com.longer.service.user.AbsUserService;
import com.longer.service.user.vo.SysUsers;

@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class QueryListUsers extends AbsUserService implements SysQueryListService<SysUsers> {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	private String groupId;

	private String roleId;
	
	private String loginName;
	
	private String[] fields;

	private List<SysUser> sysUsers = new ArrayList<>();

	
	protected QueryListUsers addLoginName(String loginName) {
		this.loginName = loginName;
		return this;
	}

	protected QueryListUsers addFields(String[] fields) {
		this.fields = fields;
		return this;
	}
	
	protected QueryListUsers addGroupId(String groupId) {
		this.groupId = groupId;
		return this;
	}

	protected QueryListUsers addRoleId(String roleId) {
		this.roleId = roleId;
		return this;
	}

	@Override
	public void execute() throws Exception {
		Map<String, Object> map = new HashMap<>();
		if (StringUtils.isNotEmpty(groupId)) {
			map.put(MODEL_GROUPID, groupId);
		}
		if (StringUtils.isNotEmpty(roleId)) {
			map.put(MODEL_ROLEID, roleId);
		}
		if(StringUtils.isNotEmpty(loginName)) {
			map.put(MODEL_LOGINNAME,loginName);
		}
		logger.info("查询用户集合的参数MODEL_GROUPID = {}, MODEL_ROLEID = {}",groupId ,roleId);
		sysUsers = userMapper.selectByQueryId(map);
		logger.info("查询到的用户及为UserList:"+sysUsers.toString());

	}
	

	@Override
	public List<SysUsers> resultObj() throws Exception{
		// 返回用户对手集合
		List<SysUsers> lists = new ArrayList<>();
		sysUsers.forEach(sUser -> {
			try {
				SysUsers user = transferObjectFields(sUser, SysUsers.class, fields);
				user.setLoginPsw(null);// 前台不显示--登录密码   手动设置为null
				lists.add(user);
			} catch (Exception e) {
				logger.error("数据库对象转化为VO对象失败e:" + e);
				throw new ChannelException("数据库对象转化为VO对象失败！", e);
			}
		});
		return lists;
	}

}
