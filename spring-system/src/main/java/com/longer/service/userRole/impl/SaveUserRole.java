/*
 * 文 件 名:  SaveUserRole.java
 * 版    权:  ETOC 信息技术有限公司, Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  longlong
 * 修改时间:  2019年1月9日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.longer.service.userRole.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.etoc.exception.ChannelException;
import com.longer.base.SysSaveService;
import com.longer.dao.model.SysUserRole;
import com.longer.service.userRole.AbsUserRoleService;
import com.longer.service.userRole.vo.UserRole;

/**
 * 添加用户和资源关联表
 * 
 * @author longlong
 * @version [版本号, 2019年1月9日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class SaveUserRole extends AbsUserRoleService implements SysSaveService<List<UserRole>> {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private List<SysUserRole> lists = new ArrayList<>();

	protected SaveUserRole addUserRoleList(List<SysUserRole> lists) {
		this.lists = lists;
		return this;
	}

	/** {@inheritDoc} */

	@Override
	public void execute() throws Exception {
		if (CollectionUtils.isNotEmpty(lists)) {
			this.lists.forEach(URoele -> {
				sysUserRoleMapper.insertSelective(URoele);
			});
			logger.info("保存角色和用户的关联表成功。");
		}else {
			logger.warn("保存的角色和用户的关联表为空@!!!");
		}
	}

	/** {@inheritDoc} */

	@Override
	public List<UserRole> resultObj() throws Exception {
		List<UserRole> lists = new ArrayList<>();
		this.lists.forEach(URoele -> {
			try {
				UserRole userR = transferObjectFields(URoele, UserRole.class, null);
				lists.add(userR);
			} catch (Exception e) {
				logger.error("数据库对象转化为VO对象失败e:" + e);
				throw new ChannelException("数据库对象转化为VO对象失败！", e);
			}
		});
		return lists;
	}

}
