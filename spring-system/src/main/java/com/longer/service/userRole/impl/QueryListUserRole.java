package com.longer.service.userRole.impl;

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
import com.longer.dao.model.SysUserRole;
import com.longer.service.userRole.AbsUserRoleService;
import com.longer.service.userRole.vo.UserRole;

/**
 * 
 * 查找用户资源关联表 <功能详细描述>
 * 
 * @author liuxiaolong
 * @version [版本号, 2019年1月9日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class QueryListUserRole extends AbsUserRoleService implements SysQueryListService<UserRole> {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private String roleId;

	private String userId;


	private List<SysUserRole> uRole = new ArrayList<>();

	protected QueryListUserRole addRoleId(String roleId) {
		this.roleId = roleId;
		return this;
	}


	protected QueryListUserRole addUserId(String userId) {
		this.userId = userId;
		return this;
	}

	@Override
	public void execute() throws Exception {
		Map<String, Object> map = new HashMap<>();
		if (StringUtils.isNotEmpty(userId)) {
			// 根据用户ID 查询关联表数据
			map.put(MODEL_USERID, userId);
		}
		if (StringUtils.isNotEmpty(roleId)) {
			// 根据角色id查询用户信息
			map.put(MODEL_ROLEID, roleId);
		} 
		
		// 当参数不为空时执行sql语句
		if(map.size() != 0) {
			uRole = sysUserRoleMapper.selectByQuery(map);
		}
		if(CollectionUtils.isEmpty(uRole)) {
			logger.warn("用户和角色关联表查询结果为空");
		}
	}

	/*
	 * 返回结果集
	 */
	@Override
	public List<UserRole> resultObj() throws Exception {
		List<UserRole> userRoles = new ArrayList<>();
		this.uRole.forEach(userRole -> {
			try {
				UserRole URole = transferObjectFields(userRole, UserRole.class, null);
				userRoles.add(URole);
			} catch (Exception e) {
				logger.error("数据库对象转化为VO对象失败e:" + e);
				throw new ChannelException("数据库对象转化为VO对象失败！", e);
			}
		});
		return userRoles;
	}

}
