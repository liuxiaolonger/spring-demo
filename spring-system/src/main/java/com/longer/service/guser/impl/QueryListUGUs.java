package com.longer.service.guser.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.etoc.exception.ChannelException;
import com.longer.base.impl.SysQueryListService;
import com.longer.dao.model.SysUserGroupUser;
import com.longer.service.guser.AbsUGroupUserService;
import com.longer.service.guser.vo.UserGroupUser;

/**
 * 
 * 查询用户组关联表信息
 * 
 * @author chenzhi
 * @version [版本号, 2018年12月24日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class QueryListUGUs extends AbsUGroupUserService implements SysQueryListService<UserGroupUser> {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private String groupId;

	private String userId;

	private List<SysUserGroupUser> gusers = new ArrayList<>();

	protected QueryListUGUs addGroupId(String groupId) {
		this.groupId = groupId;
		return this;
	}

	protected QueryListUGUs addUserId(String userId) {
		this.userId = userId;
		return this;
	}

	@Override
	public void execute() {
		Map<String, Object> map = new HashMap<>();
		if (StringUtils.isNotEmpty(userId)) {
			// 根据用户ID 查询关联表数据
			map.put(MODEL_USERID, userId);
		
		}
		if (StringUtils.isNotEmpty(groupId)) {
			// 根据用户组ID 查询关联表数据
			map.put(MODEL_GROUPID, groupId);
		} 

		// 当参数不为空时执行sql语句
		if(map.size() != 0) {
			gusers = sysUGroupUserMapper.serlectByParms(map);
		}
		if (CollectionUtils.isEmpty(gusers)) {
			logger.warn("关联表为空!@!!");
		}

	}

	/*
	 * 返回结果集
	 */
	@Override
	public List<UserGroupUser> resultObj() throws Exception{
		List<UserGroupUser> groupUsers = new ArrayList<>();
		gusers.forEach(sysUserGroup -> {
			try {
				UserGroupUser userGroup = transferObjectFields(sysUserGroup, UserGroupUser.class, null);
				groupUsers.add(userGroup);
			} catch (Exception e) {
				logger.error("数据库对象转化为VO对象失败！" + e);
				throw new ChannelException("数据库对象转化为VO对象失败！", e);
			}
		});
		return groupUsers;
	}

}
