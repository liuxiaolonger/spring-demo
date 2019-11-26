/*
 * 文 件 名:  SysUserGroupController.java
 * 版    权:  ETOC 信息技术有限公司, Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  longlong
 * 修改时间:  2018年8月14日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.longer.service.group;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.etoc.constant.DataType;
import com.etoc.exception.ChannelException;
import com.etoc.vo.StateInstance;
import com.longer.ContextHolder;
import com.longer.base.SysQueryService;
import com.longer.base.SysSaveService;
import com.longer.service.group.impl.UserGroupFacade;
import com.longer.service.group.vo.UserGroups;

/**
 * 用户组
 * 
 * @author longer
 * @version [版本号, 2018年8月14日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@RestController
@RequestMapping("/userGroups")
public class SysUserGroupController {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	protected ContextHolder context;

	/**
	 * 1.分页查询用户组信息 								dataType 为空或Page 
	 * 2.查询可用的用户组id和name组成下拉菜单 			dataType 为List      fields只有id和name
	 * 
	 * @param pageNum 		第几页 
	 * @param pageSize		 页大小
	 * @param userName		用户名称   	
	 * @param DataType List、Page、tree		返回结果集类型
	 * @param fields[] 		返回字段    
	 * @return json(List/Page/Tree)
	 * @see [类、类#方法、类#成员]
	 */
	@GetMapping(value = "/", produces = { "application/json;charset=UTF-8" })
	public ResponseEntity<?> searchGroup(Integer pageNum, Integer pageSize, String groupName, DataType dataType, String[] fields) {

		logger.info("分页查询用户组信息,param为: pageNum: {}, pageSize: {} ,groupName = {} ,dataType = {} ,fields = {}", 
				pageNum, pageSize, groupName, dataType, Arrays.toString(fields));

		// 查询用户组信息 
		UserGroupFacade userGroupService = context.getBean(UserGroupFacade.class);
		userGroupService.addUserGroupName(groupName);

		try {
			SysQueryService<?> baseService = userGroupService.queryUserGroup(pageNum, pageSize, dataType, fields);
			Object obj = baseService.resultObj();// 查询结果

			logger.info("查询结果为:" + obj);
			return new ResponseEntity<Object>(obj, HttpStatus.OK);
		} catch (ChannelException channelException) {
			throw channelException;
		} catch (Exception e) {
			logger.error("查询用户组信息失败！" , e);
			throw new ChannelException("查询用户组信息失败！", e);
		}
	}


	/**
	 * 新增加用户组信息,
	 *当关联的ids不为空时，在关联表中添加对应的用户资源
	 * 
	 * @param userGroup 将创建的用户组信息
	 * @see [类、类#方法、类#成员]
	 */
	@PostMapping(value = "/", produces = { "application/json;charset=UTF-8" })
	public ResponseEntity<?> add(@RequestBody UserGroups userGroup) {

		logger.info("新增加用户组信息入参:userGroup对象= {} " , userGroup);
		// 新增用户组
		UserGroupFacade userGroupService = context.getBean(UserGroupFacade.class);
		userGroupService.setUserGroups(userGroup);
		try {
			SysSaveService<?> uGroup = userGroupService.saveUserGroup();
			Object obj = uGroup.resultObj();

			logger.info("新增加用户组信息: {} " , obj);
			return new ResponseEntity<Object>(obj, HttpStatus.CREATED);
		} catch (ChannelException channelException) {
			throw channelException;
		} catch (Exception e) {
			logger.error("新增加用户组信息失败！" , e);
			throw new ChannelException("新增加用户组信息失败！", e);
		}

	}

	/**
	 * 根据id查询用户组信息
	 * 
	 * @param id 用户组id
	 * @return 用户组信息
	 * @see [类、类#方法、类#成员]
	 */
	@GetMapping(value = "/{id}/", produces = { "application/json;charset=UTF-8" })
	public ResponseEntity<?> get(@PathVariable(name = "id", required = true) String userGroupId , String[] fields) {
		logger.info("根据id查询用户组信息入参为:userGroupId = {},fields = {}" , userGroupId, Arrays.toString(fields));

		UserGroupService userGroupService = context.getBean(UserGroupFacade.class);
		userGroupService.addUserGroupId(userGroupId);
		userGroupService.addFields(fields);
		userGroupService.addPolicyName(AbsUserGroupService.POLICY_GROUP);
		try {
			SysQueryService<?> uGroup = userGroupService.queryUserGroup();
			Object obj = uGroup.resultObj();

			logger.info("根据id查询用户组信出参为：{}" , obj);
			return new ResponseEntity<Object>(obj, HttpStatus.OK);
		} catch (ChannelException channelException) {
			throw channelException;
		} catch (Exception e) {
			logger.error("根据id查询用户组信息失败！" , e);
			throw new ChannelException("根据id查询用户组信息失败！", e);
		}
	}

	/**
	 * 根据id修改用户组,
	 *当关联的ids不为空时，跟新关联表中对应的用户资源
	 * 
	 * @param id        用户组id
	 * @param userGroup 将修改的用户组信息
	 * @see [类、类#方法、类#成员]
	 */
	@PatchMapping(value = "/{id}/")
	public ResponseEntity<?> modify(@PathVariable(value = "id", required = true) String userGroupId,@RequestBody UserGroups userGroup) {
		logger.info("根据id修改用户组入参为userGroupId : {}, 修改的对象userGroup : {}", userGroupId, userGroup);
		UserGroupService userGroupService = context.getBean(UserGroupFacade.class);
		userGroupService.addUserGroupId(userGroupId);
		userGroupService.setUserGroups(userGroup);

		try {
			userGroupService.moveUserGroup();
		} catch (ChannelException channelException) {
			throw channelException;
		} catch (Exception e) {
			logger.error("根据id修改用户组信息失败！{}" , e);
			throw new ChannelException("根据id修改用户组信息失败！", e);
		}
		logger.error("根据id修改用户组信息成功！！");
		return new ResponseEntity<Object>(null, HttpStatus.NO_CONTENT);
	}

	/**
	 * 根据id查询用户组关联的用户集
	 * 
	 * @param userGroupId 用户组id
	 * @return 用户组关联的用户集
	 * @see [类、类#方法、类#成员]
	 */
	@GetMapping(value = "/{id}/users/", produces = { "application/json;charset=UTF-8" })
	public ResponseEntity<?> getUsers(@PathVariable(name = "id", required = true) String userGroupId, String[] fields) {
		logger.info("根据id查询用户组关联的用户集入参为: id = {} , fields = {}" , userGroupId, Arrays.toString(fields));

		// 获取用户用户集实例，批量查询对应用户
		UserGroupService userGroupService = context.getBean(UserGroupFacade.class);
		userGroupService.addFields(fields);
		userGroupService.addUserGroupId(userGroupId);
		userGroupService.addPolicyName(AbsUserGroupService.POLICY_USER);
		try {
			// 查询关联的用户信息
			SysQueryService<?> baseService = userGroupService.queryUserGroup();

			Object obj = baseService.resultObj();
			logger.info("用户组关联的用户集为  :  {} " , obj);
			
			return new ResponseEntity<Object>(obj, HttpStatus.OK);
		} catch (ChannelException channelException) {
			throw channelException;
		} catch (Exception e) {
			logger.error("根据id修改用户组信息失败！" , e);
			throw new ChannelException("根据用户组id查询关联的用户集失败！", e);
		}

	}
	
	

	/**
	 * 根据id删除用户组,以及对应的用户资源，单个删除
	 * 
	 * @param id 用户组id
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@DeleteMapping(value = "/{id}/")
	public ResponseEntity<?> remove(@PathVariable(name = "id", required = true) String id) {
		logger.info("根据id删除用户组,入参为: id = {}" , id);

		UserGroupService userGroupService = context.getBean(UserGroupFacade.class);
		userGroupService.addUserGroupId(id);
		try {
			// 删除用户组信息、级联删除用户组关联表
			userGroupService.removeUserGroup();
		} catch (ChannelException channelException) {
			throw channelException;
		} catch (Exception e) {
			logger.error("根据id删除用户组失败！" , e);
			throw new ChannelException("根据id删除用户组失败！", e);
		}
		logger.info("根据id删除用户组成功！！");
		return new ResponseEntity<Object>(null, HttpStatus.NO_CONTENT);
	}

	/**
	 * 批量修改用户组状态，暂停或启用或注销
	 * 
	 * @param lists 用户组id和状态集合
	 * @see [类、类#方法、类#成员]
	 */
	@PatchMapping(value = "/status/")
	public ResponseEntity<?> batchUserGroup(@RequestBody List<StateInstance> lists) {
		logger.info("批量修改用户组状态入参为:stateIns的数量 = {}" , lists.size());
		if (CollectionUtils.isEmpty(lists)) {
			throw new ChannelException("参数为空！");
		}
		try {
			UserGroupService userGroupService = context.getBean(UserGroupFacade.class);
			userGroupService.moveUserGroup(lists);
			
			logger.info("批量修改用户组状态成功！！");
			return new ResponseEntity<Object>(null, HttpStatus.NO_CONTENT);
		} catch (ChannelException channelException) {
			throw channelException;
		} catch (Exception e) {
			logger.error("批量修改用户组状态失败！" + e);
			throw new ChannelException("批量修改用户组状态失败！", e);
		}
	}

}
