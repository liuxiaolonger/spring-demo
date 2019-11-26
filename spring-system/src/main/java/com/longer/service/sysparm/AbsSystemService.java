package com.longer.service.sysparm;

import org.springframework.beans.factory.annotation.Autowired;

import com.longer.ContextHolder;
import com.longer.dao.mapper.SystemInfoMapper;
import com.longer.service.AbsService;

/**
 * 
 * 系统参数抽象类
 * <功能详细描述>
 * 
 * @author  chuyh
 * @version  [版本号, 2019年1月9日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public abstract class AbsSystemService extends AbsService
{
    @Autowired
    protected ContextHolder context;
    
    @Autowired
    protected SystemInfoMapper systemInfoMapper;
    
    //默认的显示字段(自动隐藏敏感和不需要的字段)
    protected String[] defaultFiedlds =
        {"systemId", "systemName", "systemKey", "systemVal", "valType", "valLength", "systemDesc"};
    
    // 需要互相映射的字段("/"分隔,前面是resource字段名,后面是model字段名)
    private static final String[] transferField = {};
    
    @Override
    protected String[] transferField()
    {
        return transferField;
    }
    
}
