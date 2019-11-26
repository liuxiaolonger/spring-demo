package com.etoc.service.sysparm.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.etoc.base.impl.SysQueryListService;
import com.etoc.exception.ChannelException;
import com.etoc.model.SystemInfo;
import com.etoc.service.sysparm.AbsSystemService;
import com.etoc.service.sysparm.vo.SystemInfoResource;
import com.etoc.util.StringUtil;

/**
 * 
 * 分页查询系统参数
 * <功能详细描述>
 * 
 * @author  chuyh
 * @version  [版本号, 2019年1月9日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Service("queryListSystem")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class QueryListSystem extends AbsSystemService implements SysQueryListService<SystemInfoResource>
{
    
    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    /*
     * 主键
     */
    private String systemId;
    /*
     * 参数名称
     */
    private String systemName;
    /*
     * 参数键
     */
    private String systemKey;
    /*
     * 参数值
     */
    private String systemValue;
    
    private List<SystemInfo> lists = new ArrayList<>();
    
    protected QueryListSystem setQueryParams(String systemId, String systemName, String systemKey, String systemValue,
        String[] fields)
    {
        this.systemId = systemId;
        this.systemName = systemName;
        this.systemKey = systemKey;
        this.systemValue = systemValue;
        this.defaultFiedlds = fields;
        return this;
    }
    
    @Override
    public List<SystemInfoResource> resultObj()
        throws Exception
    {
        if (CollectionUtils.isEmpty(lists))
        {
            return null;
        }
        //进行资源转换
        List<SystemInfoResource> results = new ArrayList<>();
        lists.forEach(dto -> {
            SystemInfoResource resource = null;
            try
            {
                resource = transferObjectFields(dto, SystemInfoResource.class, defaultFiedlds);
            }
            catch (Exception e)
            {
                logger.error("资源转换异常", e);
                throw new ChannelException("资源转换异常", e);
            }
            results.add(resource);
        });
        
        return results;
    }
    
    @Override
    public void execute()
        throws Exception
    {
        logger.info("开始系统参数集合查询...");
        Map<String, Object> map = new HashMap<String, Object>();
        if (StringUtil.isNotEmpty(systemId))
        {
            map.put("systemId", systemId);
        }
        if (StringUtil.isNotEmpty(systemName))
        {
            map.put("systemName", systemName);
        }
        if (StringUtil.isNotEmpty(systemValue))
        {
            map.put("systemVal", systemValue);
        }
        if (StringUtil.isNotEmpty(systemKey))
        {
            map.put("systemKey", systemKey);
        }
        lists = systemInfoMapper.selectBy(map);
        logger.info("查询的结果为: {}", lists);
        
    }
    
}
