package com.longer.service.organization;


import org.springframework.beans.factory.annotation.Autowired;

import com.longer.ContextHolder;
import com.longer.dao.mapper.SysOrganizationMapper;
import com.longer.service.AbsService;


/**
 * 
 *  抽象service
 * <功能详细描述>
 * 
 * @author  chuyh
 * @version  [版本号, 2019年1月8日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public abstract class AbsOrganizationService extends AbsService{
	@Autowired
	protected ContextHolder context;

	@Autowired
	protected SysOrganizationMapper sysOrganizationMapper;
	
	//默认的排序规则 这里用mybaties的XML做最合适
	//protected String[] defaultSorts= {"+code"};
	
	//默认的显示字段(自动隐藏敏感和不需要的字段)
	protected String[] defaultFiedlds= {"organizationId","name","code","priority","parentCode","parentCodes","available"};
	
	// 需要互相映射的字段("/"分隔,前面是resource字段名,后面是model字段名)
    private static final String[] transferField = {"organizationId/id"}; 
    
    @Override
    protected String[] transferField()
    {
        return transferField;
    }
}
