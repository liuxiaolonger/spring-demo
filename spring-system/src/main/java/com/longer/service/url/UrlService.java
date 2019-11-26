package com.longer.service.url;

import com.etoc.constant.DataType;
import com.longer.base.BaseService;
import com.longer.base.SysQueryService;
import com.longer.service.url.impl.SaveUrl;
import com.longer.service.url.vo.SysUrlFilterResource;
import com.longer.service.url.vo.Url;

/**
 * 
 * url信息接口
 * <功能详细描述>
 * 
 * @author  liuxiaolong
 * @version  [版本号, 2019年1月8日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface UrlService
{
    
    UrlService addQueryId(String urlId);
    
    UrlService addQueryName(String urlName);
    
    UrlService setObject(Url url);
    
    /**
     * 查询URL信息
     * <功能详细描述>
     * @return
     * @throws Exception
     * @see [类、类#方法、类#成员]
     */
    SysQueryService<SysUrlFilterResource> queryUrl(String[] fields)
        throws Exception;
    
    /**
     * 查询URL信息
     * <功能详细描述>
     * @param pageNum
     * @param pageSize
     * @param dataType
     * @param fields
     * @return
     * @throws Exception
     * @see [类、类#方法、类#成员]
     */
    SysQueryService<?> queryUrl(Integer pageNum, Integer pageSize, DataType dataType, String[] fields)
        throws Exception;
    
    /**
     * 增加动态url权限的信息
     * <功能详细描述>
     * @return
     * @throws Exception
     * @see [类、类#方法、类#成员]
     */
    SaveUrl saveUrl()
        throws Exception;
    
    /**
     * 修改动态url权限的信息
     * <功能详细描述>
     * @return
     * @throws Exception
     * @see [类、类#方法、类#成员]
     */
    BaseService moveUrl()
        throws Exception;
    
    /**
     * 删除动态url权限的信息
     * <功能详细描述>
     * @return
     * @throws Exception
     * @see [类、类#方法、类#成员]
     */
    BaseService removeUrl()
        throws Exception;
}
