package com.longer.service.organization;

import java.util.List;

import com.etoc.constant.DataType;
import com.etoc.vo.StateInstance;
import com.longer.base.BaseService;
import com.longer.base.SysQueryService;
import com.longer.base.SysSaveService;
import com.longer.service.organization.vo.Organization;
import com.longer.service.organization.vo.SysOrganizationResource;

/**
 * 
 * .组织机构Service
 * <功能详细描述>
 * 
 * @author  chuyh
 * @version  [版本号, 2019年1月7日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface OrganizationService
{

    /**
     * .增加查询参数id
     * <功能详细描述>
     * @param id
     * @return
     * @see [类、类#方法、类#成员]
     */
    OrganizationService addQueryId(String id);
    
    /**
     * .增加查询参数name
     * <功能详细描述>
     * @param name
     * @return
     * @see [类、类#方法、类#成员]
     */
    OrganizationService addQueryName(String name);
    
    /**
     * .增加查询参数code
     * <功能详细描述>
     * @param code
     * @return
     * @see [类、类#方法、类#成员]
     */
    OrganizationService addQueryCode(String code);
    
    /**
     * .增加查询参数available
     * <功能详细描述>
     * @param available
     * @return
     * @see [类、类#方法、类#成员]
     */
    OrganizationService addAvailable(String available);
        
   
   /**
    * .设置保存、修改的对象
    * <功能详细描述>
    * @param organization
    * @return
    * @see [类、类#方法、类#成员]
    */
    OrganizationService setObject(Organization organization);

    /**
     * .查询单个组织信息
     * <功能详细描述>
     * @param fields
     * @return
     * @throws Exception
     * @see [类、类#方法、类#成员]
     */
    SysQueryService<SysOrganizationResource> queryOrganization( String[] fields) throws Exception;
    
    
    
    /**
     * .查询组织信息
     * <功能详细描述>
     * @param pageNum
     * @param pageSize
     * @param dataType
     * @param fields
     * @return
     * @throws Exception
     * @see [类、类#方法、类#成员]
     */
    SysQueryService<?> queryOrganization(Integer pageNum, Integer pageSize, DataType dataType, String[] fields,String[] sort) throws Exception;
    

    /**
     * .修改机构信息
     * <功能详细描述>
     * @return
     * @throws Exception
     * @see [类、类#方法、类#成员]
     */
    BaseService moveOrganization() throws Exception;
    
    /**
     *  .修改机构信息
     * <功能详细描述>
     * @param states
     * @return
     * @throws Exception
     * @see [类、类#方法、类#成员]
     */
    BaseService moveOrganization(List<StateInstance> states) throws Exception;

    /**
     * .新增加组织机构信息
     * <功能详细描述>
     * @return
     * @throws Exception
     * @see [类、类#方法、类#成员]
     */
    SysSaveService<?> saveOrganization() throws Exception;
    
    
    
}
