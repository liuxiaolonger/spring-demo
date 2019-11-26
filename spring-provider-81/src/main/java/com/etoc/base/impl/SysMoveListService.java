package com.etoc.base.impl;
import java.util.List;

import com.etoc.base.SysMoveService;

/**
 * 
 * 批量修改
 * <功能详细描述>
 * 
 * @author  chenzhi
 * @version  [版本号, 2018年12月24日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface SysMoveListService<R> extends SysMoveService
{
    
    SysMoveListService<R> addObject(List<R> obj);
    
}
