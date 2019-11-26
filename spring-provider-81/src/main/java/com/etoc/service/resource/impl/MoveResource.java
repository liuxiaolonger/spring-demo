package com.etoc.service.resource.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.etoc.base.SysMoveService;
import com.etoc.constant.CommonConst;
import com.etoc.exception.ChannelException;
import com.etoc.model.SysResource;
import com.etoc.service.resource.AbsResourceService;
import com.etoc.service.resource.vo.Resource;

/**
 * 
 * 查询资源信息
 * 输入参数：资源模型DTO(Resource)
 * 
 * @author  liuxiaolong
 * @version  [版本号, 2019年1月9日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Service("moveResource")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class MoveResource extends AbsResourceService implements SysMoveService
{
    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    /*
    * 修改时入参对象
    */
    private Resource resource;
    
    /*
     * 修改时入参对象
     */
    private String resourceId;
    
    protected MoveResource setResourceId(String resourceId)
    {
        this.resourceId = resourceId;
        return this;
    }
    
    protected MoveResource setObject(Resource resource)
    {
        this.resource = resource;
        return this;
    }
    
    @Override
    public void execute()
        throws Exception
    {
        resource.setResourceId(resourceId);
        resource.setSystemType(CommonConst.system.STATUS_ONE);
        SysResource reResource = transferObjectFields(resource, SysResource.class, null);
       
        if (!objectIsEmpty(reResource, MODEL_ID))
        {
            // 修改角色
            Integer count = resourceMapper.updateByPrimaryKeySelective(reResource);
            logger.info("修改resource信息的条数为{}条", count);
            if (count == 0)
            {
                logger.error("修改resource未成功！");
            }
        }
        else {
            logger.info("修改resource参数不能为空");
            throw new ChannelException("修改resource参数不能为空！");
        }
       
        
    }
    
}
