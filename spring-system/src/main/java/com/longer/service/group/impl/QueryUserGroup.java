package com.longer.service.group.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.etoc.exception.ChannelException;
import com.longer.base.SysQueryService;
import com.longer.base.impl.SysQueryListService;
import com.longer.dao.model.SysUserGroup;
import com.longer.service.group.AbsUserGroupService;
import com.longer.service.group.vo.SysUserGroupResource;
import com.longer.service.guser.UGroupUserService;
import com.longer.service.guser.vo.UserGroupUser;

/**
 * 
 * 查询用户组信息 级联查询用户组关联表
 * 
 * @author chenzhi
 * @version [版本号, 2018年12月21日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class QueryUserGroup extends AbsUserGroupService implements SysQueryService<SysUserGroupResource> {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private String groupId;
	
	private String[] fields;

	private UGroupUserService uGroupUserService;

	private SysQueryListService<UserGroupUser> queryListService;

	private SysUserGroup sysUserGroup;// 保存用户组对象

	private List<String> userIds = new ArrayList<>();// 保存用户组对应的用户id集合

	
	protected QueryUserGroup addQueryId(String groupId) {
		this.groupId = groupId;
		return this;
	}
	
	protected QueryUserGroup addFields(String[] fields) {
		this.fields = fields;
		return this;
	}

	protected QueryUserGroup setUGroupUserService(UGroupUserService uGroupUserService) {
		this.uGroupUserService = uGroupUserService;
		return this;
	}

	@Override
	public void execute() throws Exception {
		// 查询用户组信息
		sysUserGroup = userGroupMapper.serlectByGroupId(groupId);
		if (sysUserGroup == null) {
			logger.error("查询用户组信息失败@！！！");
			throw new ChannelException("查询用户组信息失败@！！！");
		}

		// 查询改用户组对应的用户信息
		queryListService = uGroupUserService.queryUGroupUser();
		List<UserGroupUser> UGList = queryListService.resultObj();
		UGList.forEach( Gu -> {
			userIds.add(Gu.getUserId());
		});
		
	}

	@Override
	public SysUserGroupResource resultObj() throws Exception{
		try {
			SysUserGroupResource userGrou =  transferObjectFields(sysUserGroup, SysUserGroupResource.class, fields);
			userGrou.setUserIds(userIds);

			return userGrou;
		} catch (Exception e) {
			logger.error("数据库对象转化为VO对象失败！" + e);
			throw new ChannelException("数据库对象转化为VO对象失败！", e);
		}

	}

}
