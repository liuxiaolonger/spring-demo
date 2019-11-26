package com.longer.service.role;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
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
import com.etoc.exception.ChannelObjectNotFoundException;
import com.etoc.vo.StateInstance;
import com.longer.ContextHolder;
import com.longer.base.SysQueryService;
import com.longer.service.role.impl.RoleServiceFacade;
import com.longer.service.role.impl.SaveRole;
import com.longer.service.role.vo.Role;



/**
 * 
 * 角色信息管理接口
 * 
 * @author liuxiaolong
 * @version [版本号, 2018年7月31日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */

@RestController
@RequestMapping("/roles")
public class RoleController {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	protected ContextHolder context;

	/**
	 * 1.分页查询角色信息 2.查询所有角色 List 3.新增,编辑时验证角色名 List
	 * 
	 * @param pageNum   当前页
	 * @param pageSize  每页记录数
	 * @param sysRole   分页查询条件
	 * @param fields    需要返回的字段
	 * @param dataType  返回的数据结构
	 * @param roleName  角色名
	 * @param roleId    角色id
	 * @param available 状态
	 * @return 分页信息
	 * @throws ChannelObjectNotFoundException 如果未查到数据则返回异常
	 * @see [类、类#方法、类#成员]
	 */
	@GetMapping(value = "/", produces = { "application/json;charset=UTF-8" })
	/* @ResponseStatus(HttpStatus.OK) */
	public ResponseEntity<?> search(Integer pageNum, Integer pageSize, String roleName, DataType dataType,
			String[] fields, String roleId, String available) {
		logger.info(
				"开始查询url权限的信息 ,parames为pageNum:{},pageSize:{},roleName:{},dataType:{},fields:{},roleId:{},available:{}",
				pageNum, pageSize, roleName, dataType, fields, roleId, available);
		// 查询结果
		Object obj = null;
		try {
			RoleService roleService = context.getBean(RoleService.class).addQueryName(roleName).addQueryId(roleId)
					.addAvailable(available);
			SysQueryService<?> baseService = roleService.queryRole(pageNum, pageSize, dataType, fields);
			obj = baseService.resultObj();
		} catch (ChannelException e) {

			throw e;
		} catch (Exception e) {
			logger.error("查询角色未成功！", e);
			throw new ChannelException("查询角色未成功！", e);
		}
		logger.info("查询结果为:{}", obj);
		return new ResponseEntity<Object>(obj, HttpStatus.OK);
	}

	/**
	 * 根据角色id查询角色信息
	 * 
	 * @param fields 需要返回的字段
	 * @param roleId 角色id
	 * @return 角色信息
	 * @see [类、类#方法、类#成员]
	 */
	@GetMapping(value = "/{id}/", produces = { "application/json;charset=UTF-8" })
	/* @ResponseStatus(HttpStatus.OK) */
	public ResponseEntity<?> get(@PathVariable(name = "id", required = true) String id, String[] fields,
			Integer isLeaf) {
		logger.info("根据id查询角色信息入参为:id:{},fields{},isLeaf {} ", id, fields, isLeaf);
		Object obj = null;
		try {
			RoleService roleService = context.getBean(RoleService.class).addQueryId(id).addIsLeaf(isLeaf)
					.setFields(fields).addPolicyName(AbsRoleService.POLICY_ROLE);
			SysQueryService<?> roleResouce = roleService.queryRole();
			obj = roleResouce.resultObj();
		} catch (ChannelException e) {

			throw e;
		} catch (Exception e) {
			logger.error("根据id查询角色信息失败！", e);
			throw new ChannelException("根据id角色信息信息失败！", e);
		}
		logger.info("根据id查询角色信息出参为：{}", obj);
		return new ResponseEntity<Object>(obj, HttpStatus.OK);
	}

	/**
	 * 根据角色ID查询资源集合
	 * 
	 * @param roleId
	 * @return 资源信息集合
	 * @see [类、类#方法、类#成员]
	 */
	@GetMapping(value = "/{id}/resources/", produces = { "application/json;charset=UTF-8" })
	/* @ResponseStatus(HttpStatus.OK) */
	public ResponseEntity<?> getResourcesIdsById(@PathVariable(name = "id", required = true) String id, Integer isLeaf,
			String[] fields) {
		logger.info("根据id查询角色信息入参为:id {}, isLeaf {},fields {}", id, isLeaf, Arrays.toString(fields));
		Object roleResource = null;
		try {
			RoleService roleService = context.getBean(RoleService.class);
			roleService.addQueryId(id);
			roleService.addIsLeaf(isLeaf);
			roleService.setFields(fields);
			roleService.addPolicyName(AbsRoleService.POLICY_RESOURCE);
			SysQueryService<?> roleResouce = roleService.queryRole();
			roleResource = roleResouce.resultObj();
		} catch (ChannelException e) {

			throw e;
		} catch (Exception e) {
			logger.error("根据id查询角色信息失败！", e);
			throw new ChannelException("根据id角色信息信息失败！", e);
		}
		logger.info("根据id查询角色信息出参为：{}", roleResource);
		return new ResponseEntity<Object>(roleResource, HttpStatus.OK);
	}

	/**
	 * 根据角色ID查询用户集合 <功能详细描述>
	 * 
	 * @param id
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@GetMapping(value = "/{id}/users/", produces = { "application/json;charset=UTF-8" })
	/* @ResponseStatus(HttpStatus.OK) */
	public ResponseEntity<?> getUsersIdsById(@PathVariable(name = "id", required = true) String id) {
		logger.info("根据id查询用户组关联的用户集入参为: id:{} ", id);
		Object obj = null;

		try {
			// 获取角色用户集实例，批量查询对应用户
			RoleService roleService = context.getBean(RoleServiceFacade.class);
			roleService.addQueryId(id);
			roleService.addPolicyName(AbsRoleService.POLICY_USER);
			SysQueryService<?> baseService = roleService.queryRole();
			obj = baseService.resultObj();
		} catch (ChannelException e) {

			throw e;
		} catch (Exception e) {
			logger.error("根据id修改用户组信息失败！", e);
			throw new ChannelException("根据用户组id查询关联的用户集失败！", e);
		}
		logger.info("用户组关联的用户集为：{}", obj);
		return new ResponseEntity<Object>(obj, HttpStatus.OK);
	}

	/**
	 * 创建角色及添加对应的资源
	 * 
	 * @param sysRole 将要创建的角色信息
	 * @see [类、类#方法、类#成员]
	 */
	@PostMapping("/")
	/* @ResponseStatus(HttpStatus.CREATED) */
	public ResponseEntity<?> add(@RequestBody Role sysRole) {
		logger.info("创建角色失败入参为:sysRole:{}", sysRole);
		Object role = null;
		try {
			RoleService roleService = context.getBean(RoleServiceFacade.class).setObject(sysRole);
			SaveRole saveRole = roleService.saveRole();
			role = saveRole.resultObj();
		} catch (ChannelException e) {

			throw e;
		} catch (Exception e) {
			logger.error("创建角色失败！", e);
			throw new ChannelException("创建角色失败!", e);
		}
		logger.info("创建角色出参为，{}", role);
		return new ResponseEntity<Object>(role, HttpStatus.CREATED);
	}

	/**
	 * 批量修改角色状态 暂停或恢复 available:激活;suspend:暂停;cancel:注销
	 * 
	 * @param states 枚举集合
	 * @see [类、类#方法、类#成员]
	 */
	@PatchMapping(value = "/status/")
	public ResponseEntity<?> batchRoleStatus(@RequestBody List<StateInstance> states) {
		logger.info("开始批量修改角色状态 暂停或恢复,states:{}", states);
		if (CollectionUtils.isEmpty(states)) {
			logger.error("请输入批量修改的参数！");
			throw new ChannelException("请输入批量修改的参数！");
		}
		try {
			RoleServiceFacade resourceService = context.getBean(RoleServiceFacade.class);
			resourceService.moveRole(states);
		} catch (ChannelException e) {

			throw e;
		} catch (Exception e) {
			logger.error("批量修改角色状态失败!", e);
			throw new ChannelException("批量修改角色状态失败！", e);
		}
		logger.info("批量修改角色状态完成!");
		return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
	}

	/**
	 * 1.根据角色id修改角色 2.根据角色id修改角色 关联的资源 3.根据角色id修改角色 关联的用户
	 * 
	 * @param roleId  角色id
	 * @param sysRole 修改后的角色信息
	 * @see [类、类#方法、类#成员]
	 */
	@PatchMapping("/{id}/")
	public ResponseEntity<?> modify(@PathVariable(name = "id", required = true) String id, @RequestBody Role role) {
		logger.info("根据角色id修改角色及更新对应的资源入参为:id:{},role:{} ", id, role);
		try {
			RoleService roleService = context.getBean(RoleServiceFacade.class).setObject(role).addQueryId(id)
					.addPolicyName(AbsRoleService.POLICY_ROLE);
			roleService.moveRole();
		} catch (ChannelException e) {

			throw e;
		} catch (Exception e) {
			logger.error("修改角色信息失败！", e);
			throw new ChannelException("修改角色信息失败！", e);
		}
		logger.info("修改角色信息完成！");
		return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
	}
}
