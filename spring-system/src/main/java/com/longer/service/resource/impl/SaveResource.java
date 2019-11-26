package com.longer.service.resource.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.etoc.constant.CommonConst;
import com.etoc.constant.StateEnum;
import com.etoc.exception.ChannelException;
import com.etoc.util.UUIDUtil;
import com.longer.base.SysSaveService;
import com.longer.dao.model.SysResource;
import com.longer.service.resource.AbsResourceService;
import com.longer.service.resource.vo.Resource;

/**
 * 
 * 保存资源
 * <功能详细描述>
 * 
 * @author  liuxiaolong
 * @version  [版本号, 2019年1月8日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Service("saveResource")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class SaveResource extends AbsResourceService implements SysSaveService<Resource>
{
    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    /*
     * 保存的入参对象
     */
    private Resource resource;
    
    protected SaveResource setObject(Resource resource)
    {
        this.resource = resource;
        return this;
    }
    
    /**
     * 添加
     */
    @Override
    public void execute()
        throws Exception
    {
        //设置默认值
        resource.setResourceId(UUIDUtil.getUUID());
        resource.setAvailable(StateEnum.available.name());
        resource.setSystemType(CommonConst.system.STATUS_ONE);
        // 创建资源编码
        String parentCode = resource.getParentCode();
        String maxCode = resourceMapper.selectMaxSonCode(parentCode);
        if (null != maxCode)
        {
            String v = maxCode.substring(0, 1);
            if (v.equals("0"))
            {
                resource.setCode(v + (Long.parseLong(maxCode) + 1));
            }
            else
            {
                resource.setCode((Long.parseLong(maxCode) + 1) + "");
            }
        }
        else
        {
            resource.setCode(parentCode + "01");
        }
        SysResource reResource = transferObjectFields(resource, SysResource.class, null);
        Integer count = resourceMapper.insertSelective(reResource);
        logger.info("新增resource信息的条数为{}条", count);
        if (count == 0)
        {
            logger.info("新增resource未成功！");
            throw new ChannelException("新增resource未成功！");
        }
        
    }
    
    /**
     * 返回结果
     */
    @Override
    public Resource resultObj()
        throws Exception
    {
        
        return resource;
    }
    
}
