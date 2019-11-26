package com.etoc.service.resource.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.etoc.base.SysMoveService;
import com.etoc.constant.StateEnum;
import com.etoc.exception.ChannelException;
import com.etoc.model.SysResource;
import com.etoc.service.resource.AbsResourceService;
import com.etoc.vo.StateInstance;
/**
 * 
 *批量修改资源的状态
 * 输入参数：List<StateInstance>
 * 
 * @author  liuxiaolong
 * @version  [版本号, 2019年1月9日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Service("moveResourceState")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class MoveResourceState extends AbsResourceService implements SysMoveService
{
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
    /*
     * 批量修改状态的对象
     */
    private List<StateInstance> obj;
    
    protected MoveResourceState addObject(List<StateInstance> obj)
    {
        this.obj = obj;
        return this;
    }
    
    @Override
    public void execute()
        throws Exception
    {
        if (obj.size() == 0)
            return;
        obj.forEach(state -> {
        	SysResource resource = resourceMapper.selectByPrimaryKey(state.getId());
        	if(resource == null) {
        		logger.error("id参数错误!!!");
                throw new ChannelException("id参数错误!!");
        	}
            Map<String, Object> map = new HashMap<>();
            map.put(MODEL_ID, state.getId());
            map.put(MODEL_AVAILABLE, state.getState().name());
            resourceMapper.batchAvailable(map);// 修改节点状态
            if(StateEnum.available.equals(state.getState().name())) {
            	// 只做批量暂停子节点，不做批量激活子节点
            	return;
            }
            List<SysResource> resourceLists = resourceMapper.getNotSonResources(resource.getCode());
            if(CollectionUtils.isNotEmpty(resourceLists)) {
            	// 修改子节点状态
            	resourceLists.forEach(res ->{
            		 Map<String, Object> sonMap = new HashMap<>();
            		 sonMap.put(MODEL_ID, res.getId());
            		 sonMap.put(MODEL_AVAILABLE, state.getState().name());
                     resourceMapper.batchAvailable(sonMap);
            	});
            }
        });
    }
    
}
