package com.longer.service.organization;

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
import com.etoc.vo.StateInstance;
import com.longer.ContextHolder;
import com.longer.base.SysQueryService;
import com.longer.base.SysSaveService;
import com.longer.service.organization.impl.OrganizationFacade;
import com.longer.service.organization.vo.Organization;
import com.longer.service.organization.vo.SysOrganizationResource;

/**
 * 
 * 机构信息操作接口 <功能详细描述>
 * 
 * @author chuyh
 * @version [版本号, 2019年1月8日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */

@RestController("organizations")
@RequestMapping("/organizations")
public class SysOrganizationController {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private ContextHolder context;

	/**
	 * 1.分页查询机构信息 dataType为page时 2.全查询机构信息 dataType为list时 3.树形查询查询机构信息 dataType为tree
	 * 4.根据id查询子机构信息 id不为空时 dataType为list时 5.查询最大的code值 sort为-code时 dataType为list时
	 * 
	 * @param pageNum   当前页码
	 * @param pageSize  每页大小
	 * @param dataType  返回数据类型(Page List Tree)
	 * @param fields    需要返回参数
	 * @param id        主键
	 * @param name      机构名称
	 * @param code      机构编码
	 * @param available 状态
	 * @param sort      排序
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@GetMapping(value = "/", produces = { "application/json;charset=UTF-8" })
	// @ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Object> search(Integer pageNum, Integer pageSize, DataType dataType, String[] fields,
			String id, String name, String code, String available, String[] sort) {
		logger.info(
				"开始进入组织机构多查询,param为, pageNum= {} ,pageSize= {} ,dataType={} ,fields= {} ,id= {} ,code= {} ,available= {} ,sort= {}",
				pageNum, pageSize, dataType, fields, id, code, available, sort);
		// 查询结果
		SysQueryService<?> sysQueryService = null;
		Object object = null;
		try {
			OrganizationService organizationService = context.getBean(OrganizationFacade.class).addQueryName(name)
					.addQueryCode(code).addAvailable(available).addQueryId(id);
			sysQueryService = organizationService.queryOrganization(pageNum, pageSize, dataType, fields, sort);
			object = sysQueryService.resultObj();
		} catch (ChannelException e) {
			throw e;
		} catch (Exception e) {
			logger.error("查询机构信息失败", e);
			throw new ChannelException("查询机构信息失败", e);
		}
		logger.info("查询结果为:" + object);

		return new ResponseEntity<Object>(object, HttpStatus.OK);
		// return object;
	}

	/**
	 * 根据机构id查询机构信息
	 * 
	 * 
	 * @param organizationId 机构id
	 * @return 机构信息
	 * @see [类、类#方法、类#成员]
	 */

	@GetMapping(value = "/{id}/", produces = { "application/json;charset=UTF-8" })
	// @ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Object> get(@PathVariable(value = "id", required = true) String id, String[] fields) {

		logger.info("单个机构信息查询,参数为:id= {}, ,fields= {}", id, fields);
		// 查询结果
		SysQueryService<SysOrganizationResource> sysQueryService = null;
		Object object = null;
		try {
			OrganizationService organizationService = context.getBean(OrganizationFacade.class).addQueryId(id);
			sysQueryService = organizationService.queryOrganization(fields);
			object = sysQueryService.resultObj();
		} catch (ChannelException e) {
			throw e;
		} catch (Exception e) {
			logger.error("根据机构ID查询机构信息失败", e);
			throw new ChannelException("根据机构ID查询机构信息失败", e);
		}
		logger.info("单个机构信息查询的结果为: {}", object);
		// return object;
		return new ResponseEntity<Object>(object, HttpStatus.OK);
	}

	/**
	 * <根据机构id对机构信息进行修改
	 * 
	 * @param id              机构id
	 * @param sysOrganization 将要修改的机构信息
	 * @see [类、类#方法、类#成员]
	 */
	@PatchMapping(value = "/{id}/")
	// @SysLog("根据机构id对机构信息进行修改")
	public ResponseEntity<?> modify(@PathVariable(value = "id", required = true) String id,
			@RequestBody Organization organization) {
		logger.info("进入根据机构id对机构信息进行修改,入参为: {} ", organization);
		// 查询结果
		try {
			OrganizationService organizationService = context.getBean(OrganizationFacade.class).setObject(organization)
					.addQueryId(id);
			organizationService.moveOrganization();
		} catch (ChannelException e) {
			throw e;
		} catch (Exception e) {
			logger.error("修改机构信息失败", e);
			throw new ChannelException("修改机构信息失败");
		}
		logger.info("修改完成!!!");
		return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
	}

	/**
	 * .新增组织机构
	 * 
	 * @param sysOrganization 将要增加的组织机构信息
	 * @see [类、类#方法、类#成员]
	 */
	@PostMapping(value = "/")
	// @ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Object> add(@RequestBody Organization organization) {
		logger.info(" 新增组织机构,入参为 {}", organization);
		// 查询结果
		SysSaveService<?> sysSaveService = null;
		Organization result = null;
		try {
			OrganizationService organizationService = context.getBean(OrganizationFacade.class).setObject(organization);
			sysSaveService = organizationService.saveOrganization();
			result = (Organization) sysSaveService.resultObj();
		} catch (ChannelException e) {
			throw e;
		} catch (Exception e) {
			logger.error("新增机构信息失败", e);
			throw new ChannelException("新增机构信息失败");
		}
		logger.info("新增完成!");
		// return result;
		return new ResponseEntity<Object>(result, HttpStatus.CREATED);
	}

	/**
	 * .批量暂停或启用组织机构 <功能详细描述>
	 * 
	 * @param jsons json数组格式
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@PatchMapping(value = "/status/")
	// @SysLog("根据机构id集批量启用机构")
	public ResponseEntity<?> batchOrganizationStatus(@RequestBody List<StateInstance> status) {
		logger.info("批量暂停或启用组织机构,入参为: {}", status);
		if (CollectionUtils.isEmpty(status)) {
			logger.error("批量修改状态的参数不能为空!!");
			throw new ChannelException("批量修改状态的参数不能为空!!");
		}

		try {
			// List<StateInstance> states = JSONArray.parseArray(jsons,
			// StateInstance.class);
			OrganizationService organizationService = context.getBean(OrganizationFacade.class);
			organizationService.moveOrganization(status);
		} catch (ChannelException e) {
			throw e;
		} catch (Exception e) {
			logger.error("批量操作组织机构状态失败", e);
			throw new ChannelException("批量操作组织机构状态失败");
		}
		logger.info("批量暂停或启用组织机构完成");
		return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
	}

}
