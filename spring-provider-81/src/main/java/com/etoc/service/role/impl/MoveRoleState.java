package com.etoc.service.role.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.etoc.base.SysMoveService;
import com.etoc.service.role.AbsRoleService;
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
@Service("moveRoleState")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class MoveRoleState extends AbsRoleService implements SysMoveService
{
    /*
     * 批量修改状态的对象
     */
    private List<StateInstance> obj;
    
    protected MoveRoleState addObject(List<StateInstance> obj)
    {
        this.obj = obj;
        return this;
    }
    
    @Override
    public void execute()
        throws Exception
    {
        if (CollectionUtils.isEmpty(obj))
            
            return;
        obj.forEach(state -> {
            Map<String, Object> map = new HashMap<>();
            map.put(MODEL_ID, state.getId());
            map.put(MODEL_AVAILABLE, state.getState().name());
            sysRoleMapper.updateAvailable(map);
        });
    }
    
}
