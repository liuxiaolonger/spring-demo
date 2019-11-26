package com.longer.service.organization.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.etoc.constant.DataType;
import com.etoc.exception.ChannelException;
import com.etoc.vo.StateInstance;
import com.longer.base.BaseService;
import com.longer.base.SysQueryService;
import com.longer.base.SysSaveService;
import com.longer.service.organization.AbsOrganizationService;
import com.longer.service.organization.OrganizationService;
import com.longer.service.organization.vo.Organization;
import com.longer.service.organization.vo.SysOrganizationResource;

/**
 * 
 * .组织机构调度器 <功能详细描述>
 * 
 * @author chuyh
 * @version [版本号, 2019年1月7日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Service("organizationFacade")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class OrganizationFacade extends AbsOrganizationService implements OrganizationService {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	/*
	 * 新增和修改专用对象
	 */
	private Organization organization;

	/*
	 * 主键
	 */
	private String id;

	/*
	 * 组织机构名称
	 */
	private String name;

	/*
	 * 组织机构编码
	 */
	private String code;

	/*
	 * 组织机构状态
	 */
	private String available;

	@Override
	public OrganizationService addQueryId(String id) {
		this.id = id;
		return this;
	}

	@Override
	public OrganizationService addQueryName(String name) {
		this.name = name;
		return this;
	}

	@Override
	public OrganizationService addQueryCode(String code) {
		this.code = code;
		return this;
	}

	@Override
	public OrganizationService addAvailable(String available) {
		this.available = available;
		return this;
	}

	@Override
	public OrganizationService setObject(Organization organization) {
		this.organization = organization;
		return this;
	}

	/*
	 * 查询组织信息
	 */
	@Override
	public SysQueryService<?> queryOrganization(Integer pageNum, Integer pageSize, DataType dataType, String[] fields, String[] sort) throws Exception {
		SysQueryService<?> sysQueryService = null;
		// 设置默认分页查询
		if (dataType == null)
			dataType = DataType.Page;
		if (pageNum == null || pageSize == null) {// 设置默认分页查询的当前页和每页数量
			pageNum = 1;
			pageSize = 10;
		}
		if (DataType.Page.equals(dataType)) {
			logger.info("组织机构分页查询," + dataType);
			sysQueryService = context.getBean(QueryPageOrganization.class).setPageInfo(pageNum, pageSize, fields, sort).setQueryParams(name, code);
		} else if (DataType.Tree.equals(dataType)) {
			logger.info("组织机构树形查询," + dataType);
			sysQueryService = context.getBean(QueryTreeOrganization.class).setQueryParams(fields, available);

		}
		// 全查询和子机构查询
		else if (DataType.List.equals(dataType)) {
			logger.info("全查询和子机构查询," + dataType);
			sysQueryService = context.getBean(QueryListOrganization.class).setQueryParams(id, available, fields, sort).setQueryCode(code);
		} else {
			logger.error("参数错误");
			throw new ChannelException("参数错误");
		}

		// 执行器
		sysQueryService.execute();
		return sysQueryService;
	}

	@Override
	public SysQueryService<SysOrganizationResource> queryOrganization(String[] fields) throws Exception {
		SysQueryService<SysOrganizationResource> sysQueryService = context.getBean(QueryOrganization.class).setQueryParams(fields, id);
		// 执行器
		sysQueryService.execute();
		return sysQueryService;
	}

	/*
	 * 修改机构信息
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public BaseService moveOrganization() throws Exception {
		BaseService sysQueryService = context.getBean(MoveOrganization.class).setObject(organization).addQueryId(id);

		// 执行器
		sysQueryService.execute();
		return sysQueryService;
	}

	/*
	 * 修改机构信息
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public BaseService moveOrganization(List<StateInstance> states) throws Exception {
		BaseService sysQueryService = null;

		sysQueryService = context.getBean(MoveOrganizationState.class).addObject(states);

		// 执行器
		sysQueryService.execute();
		return sysQueryService;
	}

	/*
	 * 新增加组织机构信息
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public SysSaveService<?> saveOrganization() throws Exception {
		SysSaveService<?> sysQueryService = context.getBean(SaveOrganization.class).setObject(organization);
		// 执行器
		sysQueryService.execute();
		return sysQueryService;
	}
}
