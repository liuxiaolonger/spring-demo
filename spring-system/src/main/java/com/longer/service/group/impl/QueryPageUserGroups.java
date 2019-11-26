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

import com.etoc.exception.ChannelException;
import com.etoc.util.PageInfo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.longer.base.impl.SysQueryListService;
import com.longer.base.impl.SysQueryPageService;
import com.longer.dao.model.SysUserGroup;
import com.longer.service.group.AbsUserGroupService;
import com.longer.service.group.vo.SysUserGroupResource;
import com.longer.service.guser.AbsUGroupUserService;
import com.longer.service.guser.UGroupUserService;
import com.longer.service.guser.vo.UserGroupUser;

/**
 * 
 * 用户组批量数据业务实现 级联查询用户组关联信息
 * 
 * @author chenzhi
 * @version [版本号, 2018年12月21日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class QueryPageUserGroups extends AbsUserGroupService implements SysQueryPageService<SysUserGroupResource> {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private Integer pageNum;

	private Integer pageSize;

	private String groupName;

	private String[] fields;

	private List<SysUserGroup> groups = new ArrayList<>();

	private Map<String, List<UserGroupUser>> dateGuser = new HashMap<>();

	private UGroupUserService uGroupUserService;

	private SysQueryListService<UserGroupUser> queryListService;

	protected QueryPageUserGroups setUGroupUserService(UGroupUserService uGroupUserService) {
		this.uGroupUserService = uGroupUserService;
		return this;
	}

	protected QueryPageUserGroups addQueryName(String groupName) {
		this.groupName = groupName;
		return this;
	}

	protected QueryPageUserGroups setQueryParams(Integer pageNum, Integer pageSize) {
		this.pageNum = pageNum;
		this.pageSize = pageSize;
		return this;
	}

	protected QueryPageUserGroups setQueryParams(Integer pageNum, Integer pageSize, String[] fields) {
		this.pageNum = pageNum;
		this.pageSize = pageSize;
		this.fields = fields;
		return this;
	}

	/**
	 * 查询集合
	 */
	@Override
	public void execute() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("groupName", groupName);

		PageHelper.startPage(pageNum, pageSize);
		groups = userGroupMapper.serlectUserGroup(map);

		groups.forEach(sysGroup -> {
			try {
				// 给关联表赋值
				uGroupUserService.addQueryId(AbsUGroupUserService.GROUP_ID, sysGroup.getId());
				// 查询用户组关联表中改用户组对应的用户信息
				queryListService = uGroupUserService.queryUGroupUser();
				// 获取查询到的用户组对应的用户资源
				List<UserGroupUser> list = queryListService.resultObj();
				// 将获取到的用户组关联表对象保存到全局变量gUsers中
				dateGuser.put(sysGroup.getId(), list);

			} catch (ChannelException channelException) {
				throw channelException;
			} catch (Exception e) {
				logger.error("获取用户组关联表信息失败！" + e);
				throw new ChannelException("获取用户组关联表信息失败！", e);
			}
		});
	}

	@Override
	public PageInfo<SysUserGroupResource> resultObj() throws Exception{
		Page<SysUserGroupResource> lists = new Page<>(this.pageNum, this.pageSize);
		this.groups.forEach(sysGroup -> {
			List<UserGroupUser> gUIds = dateGuser.get(sysGroup.getId());
			List<String> userIds = new ArrayList<>();
			gUIds.forEach(gUser -> {
				userIds.add(gUser.getUserId());
			});
			try {
				SysUserGroupResource userGroups = transferObjectFields(sysGroup, SysUserGroupResource.class, fields);
				userGroups.setUserIds(userIds);
				lists.add(userGroups);
			} catch (Exception e) {
				logger.error("数据库对象转化为VO对象失败！" + e);
				throw new ChannelException("数据库对象转化为VO对象失败！", e);
			}
		});
		PageInfo<SysUserGroupResource> userGroupPage = new PageInfo<>(lists,SysUserGroupResource.class);
		userGroupPage.setTotal(((Page<SysUserGroup>) this.groups).getTotal());
		
		return userGroupPage;
	}

}
