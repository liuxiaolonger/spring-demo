/*
 * 文 件 名:  UserController.java
 * 版    权:  ETOC 信息技术有限公司, Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  longlong
 * 修改时间:  2019年1月7日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.longer.service.user;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.longer.service.user.impl.UserServiceFacade;
import com.longer.service.user.vo.SysUsers;

/**
 * 用户管理
 * 
 * @author longlong
 * @version [版本号, 2019年1月7日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@RestController
@RequestMapping("/users")
public class UserController {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	protected ContextHolder context;


	/**
	 * 1.分页查询用户信息 				dataType为 page 	   参数	pageNum  pageSize
	 * 2.查询所有的用户名与ID 			dataType为 list   fields只有id和name
	 * 3.添加用户前判断是否存在该登录名	dataType为 list 	  参数  loginName
	 * @param pageNum  当前页
	 * @param pageSize 每页记录数
	 * @param sysuser  用户信息查询条件
	 * @param dataType 返回类型
	 * @param fields	返回字段
	 * @param userName  用户名称
	 * @param likeLoginName	模糊查询登录名
	 * @param loginName 登录名
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@GetMapping(value = "/", produces = { "application/json;charset=UTF-8" })
	public ResponseEntity<?> search(Integer pageNum, Integer pageSize, DataType dataType, String[] fields,
			String userName, String loginNameLike,String loginName,String organizationId) {

		logger.info("分页查询用户信息入参为：pageNum = {},  pageSize = {},  dataType = {}, fields = {}, " + "userName = {}, loginNameLike ={} , loginName = {} , organizationId = {}", 
				pageNum, pageSize, dataType, Arrays.toString(fields), userName, loginNameLike, loginName,organizationId);

		try {
			UserServiceFacade userService = context.getBean(UserServiceFacade.class)
					.addLoginNameLike(loginNameLike).addUserName(userName).addLoginName(loginName)
					.addOrgId(organizationId);

			SysQueryService<?> baseService = userService.queryUser(pageNum, pageSize, dataType, fields);
			// 查询结果
			Object obj = baseService.resultObj();

			logger.info("分页查询用户信息出参：{}", obj);
			return new ResponseEntity<Object>(obj, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("查询用户信息失败!!", e);
			throw new ChannelException("查询用户信息失败!", e);
		}
	}

	/**
	 * 1.根据用户ID查询用户信息 参数 userId
	 * @param userId 用户id
	 * @param fields 返回字段
	 * @return 用户信息
	 * @see [类、类#方法、类#成员]
	 */
	@GetMapping(value = "/{id}/", produces = { "application/json;charset=UTF-8" })
	public ResponseEntity<?> get(@PathVariable(name = "id", required = true) String userId,String[] fields,Integer isLeaf) {
		try {
			logger.info("根据单条查询用户信息id：{} ,fields = {}, isLeaf = {}", userId, Arrays.toString(fields),isLeaf);
			UserServiceFacade userService = context.getBean(UserServiceFacade.class)
					.addUserId(userId).addFields(fields).addIsLeaf(isLeaf);
			SysQueryService<?> baseService = userService.queryUser();
			// 查询结构
			Object obj = baseService.resultObj();
			logger.info("单条查询用户信息user：{}", obj);
			return new ResponseEntity<Object>(obj, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("根据用户ID查询用户信息失败：", e);
			throw new ChannelException("根据用户ID查询用户信息失败!", e);
		}
	}



	/**
	 * 根据用户ID修改用户信息，若关联ids不为空时更新对应的关联表信息 
	 * SysUsers 当删除所有关联表信息时关联表对应的ids为" "
	 * 
	 * @param sysUser 用户对象
	 * @see [类、类#方法、类#成员]
	 */
	@PatchMapping(value = "/{id}/")
	public ResponseEntity<?> modify(@PathVariable(name = "id", required = true) String userId, 
			@RequestBody SysUsers sysUsers) {
		try {
			logger.info("修改用户信息id：{}, 修改后的对象user： {} ", userId, sysUsers);
			UserServiceFacade userService = context.getBean(UserServiceFacade.class)
					.addUserId(userId).addObject(sysUsers)
					.addPolicyName(AbsUserService.POLICY_USER);
			// 执行修改方法
			userService.moveUser();
			logger.info("修改用户成功！！");
			return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			logger.error("修改用户失败，请重新操作!", e);
			throw new ChannelException("修改用户失败，请重新操作!", e);
		}
	}

	/**
	 * 增加用户 当关联表ids不为空时，同时在关联表中添加用户对应的关联信息
	 * 
	 * @param sysUser 用户对象
	 * @return 新的用户对象
	 * @see [类、类#方法、类#成员]
	 */
	@PostMapping(value = "/", produces = { "application/json;charset=UTF-8" })
	public ResponseEntity<?> add(@RequestBody SysUsers sysUsers) {
		try {
			logger.info("增加用户user: {}", sysUsers);
			UserServiceFacade userService = context.getBean(UserServiceFacade.class).addObject(sysUsers);
			SysSaveService<?> us = userService.saveUser();

			Object obj = us.resultObj();
			logger.info("增加用户user: {}", obj);
			return new ResponseEntity<Object>(obj, HttpStatus.CREATED);
		} catch (Exception e) {
			logger.error("新增失败，请从新操作! ", e);
			throw new ChannelException("新增失败，请从新操作!", e);
		}
	}

	/**
	 * 批量修改用户状态 available=激活; suspend=暂停; cancel=注销
	 * 
	 * @param jsons
	 * @see [类、类#方法、类#成员]
	 */
	@PatchMapping(value = "/status/")
	public ResponseEntity<?> batchUserStatus(@RequestBody List<StateInstance> status) {
		try {
			logger.info("批量修改用户状态参数stateIns的数量： {}", status.size());
			UserServiceFacade userServiceFacade = context.getBean(UserServiceFacade.class);
			userServiceFacade.moveUser(status);

			logger.info("批量修改用户状态成功！！");
			return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			logger.error("批量修改用户状态失败，请重新操作!", e);
			throw new ChannelException("批量修改用户状态失败，请重新操作!", e);
		}
	}

	/**
	 * 重置密码
	 * 
	 * @param userId 用户ID
	 * @see [类、类#方法、类#成员]
	 */
	@PatchMapping(value = "/{id}/password/")
	public ResponseEntity<?> resetPassword(@PathVariable(name = "id", required = true) String userId) {
		try {
			logger.info("用户id：{} ", userId);
			UserServiceFacade userService = context.getBean(UserServiceFacade.class).addUserId(userId).addPolicyName(AbsUserService.POLICY_USERPwd);

			userService.moveUser();
			logger.info("重置密码成功！！");
			return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			logger.error("重置失败，请重新操作！", e);
			throw new ChannelException("重置失败，请重新操作！", e);
		}
	}

	/**
	 * 根据用户ID查询该用户的资源集合
	 * 
	 * @param userId 用户ID
	 * @param fields 返回字段
	 * @return 资源信息集合
	 * @see [类、类#方法、类#成员]
	 */
	@GetMapping(value = "/{id}/resources/", produces = { "application/json;charset=UTF-8" })
	public ResponseEntity<?> getByIdResources(@PathVariable(name = "id", required = true) String userId,Integer isLeaf, String available, String[] fields) {
		try {
			logger.info("用户id: {},fields = {}, isLeaf={}, available={}", userId, Arrays.toString(fields), isLeaf,available);
			UserServiceFacade userServiceFacade = context.getBean(UserServiceFacade.class)
					.addUserId(userId).addFields(fields).addAvailable(available).addIsLeaf(isLeaf)
					.addPolicyName(AbsUserService.POLICY_RESOURCE);

			SysQueryService<?> result = userServiceFacade.queryUser();
			Object obj = result.resultObj();
			logger.info("get user resources from db success!!!: {}");
			return new ResponseEntity<Object>(obj, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("根据用户ID和资源条件查询关联表信息失败!!", e);
			throw new ChannelException("根据用户ID和资源条件查询关联表信息失败，请重新操作!", e);
		}
	}



	/**
	 * 根据用户id查询角色集合
	 * 
	 * @param userId 用户ID
	 * @return 角色的集合
	 * @see [类、类#方法、类#成员]
	 */
	@GetMapping(value = "/{id}/roles/", produces = { "application/json;charset=UTF-8" })
	public ResponseEntity<?> getByIdRoles(@PathVariable(name = "id", required = true) String userId, String[] fields) {
		try {
			logger.info("用户id: {},fields = {}", userId, Arrays.toString(fields));
			UserServiceFacade userServiceFacade = context.getBean(UserServiceFacade.class)
					.addUserId(userId).addFields(fields)
					.addPolicyName(AbsUserService.POLICY_ROLE);

			SysQueryService<?> result = userServiceFacade.queryUser();
			Object obj = result.resultObj();
			logger.info("用户关联的角色集合为UserList : {} ", obj);
			return new ResponseEntity<Object>(obj, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("根据用户id查询角色集合失败，请重新操作!", e);
			throw new ChannelException("根据用户id查询角色集合失败，请重新操作!", e);
		}
	}

	/**
	 * 根据用户id查询用户组集合
	 * 
	 * @param userId 用户ID
	 * @return 角色的集合
	 * @see [类、类#方法、类#成员]
	 */
	@GetMapping(value = "/{id}/groups/", produces = { "application/json;charset=UTF-8" })
	public ResponseEntity<?> getByIdGroups(@PathVariable(name = "id", required = true) String userId, String[] fields) {
		try {
			logger.info("用户id: {},fields = {}", userId, Arrays.toString(fields));
			UserServiceFacade userServiceFacade = context.getBean(UserServiceFacade.class)
					.addUserId(userId).addFields(fields)
					.addPolicyName(AbsUserService.POLICY_GROUP);

			SysQueryService<?> result = userServiceFacade.queryUser();
			Object obj = result.resultObj();
			logger.info("用户关联的用户组集合为UserList : {} ", obj);
			return new ResponseEntity<Object>(obj, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("根据用户id查询用户组集合失败，请重新操作!", e);
			throw new ChannelException("根据用户id查询用户组集合失败，请重新操作!", e);
		}
	}

}