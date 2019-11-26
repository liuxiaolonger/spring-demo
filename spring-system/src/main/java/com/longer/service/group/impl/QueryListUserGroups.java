/*
 * 文 件 名:  QueryListUserGroups.java
 * 版    权:  ETOC 信息技术有限公司, Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  Administrator
 * 修改时间:  2019年1月7日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.longer.service.group.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.etoc.constant.StateEnum;
import com.etoc.exception.ChannelException;
import com.etoc.util.StringUtil;
import com.longer.base.impl.SysQueryListService;
import com.longer.dao.model.SysUserGroup;
import com.longer.service.group.AbsUserGroupService;
import com.longer.service.group.vo.UserGroups;

/**
 * 查询用户组下拉菜单数据
 * 
 * @author longlong
 * @version [版本号, 2019年1月7日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class QueryListUserGroups extends AbsUserGroupService implements SysQueryListService<UserGroups> {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private List<SysUserGroup> userGroups = new ArrayList<>();

	private String userId; // 用户主键id
	
	protected QueryListUserGroups addUserId(String userId) {
		this.userId = userId;
		return this;
	}
	/** {@inheritDoc} */

	@Override
	public void execute() throws Exception {
		// 获取所有状态为可用的用户组信息
		Map<String, Object> map =new HashMap<>();
		map.put(POLICY_STATUS, StateEnum.available.name());
		if(StringUtil.isNotEmpty(userId)) {
			map.put(POLICY_USERID, userId);
		}
		userGroups = userGroupMapper.serlectAllUserGroup(map);

	}

	/** {@inheritDoc} */

	@Override
	public List<UserGroups> resultObj() throws Exception {
		List<UserGroups> lists = new ArrayList<>();
		this.userGroups.forEach(Ugroup -> {
			try {
				UserGroups userGroup = transferObjectFields(Ugroup, UserGroups.class, null);
				lists.add(userGroup);
			} catch (Exception e) {
				logger.error("数据库对象转化为VO对象失败！" + e);
				throw new ChannelException("数据库对象转化为VO对象失败！", e);
			}
		});
		return lists;
	}

}
