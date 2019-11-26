package com.longer.service.role;

import org.springframework.beans.factory.annotation.Autowired;

import com.longer.ContextHolder;
import com.longer.dao.mapper.SysRoleMapper;
import com.longer.service.AbsService;

public abstract class AbsRoleService extends AbsService
{
    @Autowired
    protected ContextHolder context;
    
    @Autowired
    protected SysRoleMapper sysRoleMapper;
    
    public static final String POLICY_USERROLE = "USERROLE";
    
    public static final String ROLE_ID = "ROLE_ID";
    
    public static final String RESOURCE_ID = "RESOURCE_ID";
    
    public static final String USER_ID = "USER_ID";
    
    public static final String POLICY_ROLE = "ROLE";
    
    public static final String POLICY_RESOURCE = "RESOURCE";
    
    public static final String POLICY_USER = "USER";
    /*
     * 设置mapper层字段  角色id
     */
    protected static final String  MODEL_ID="id";
    /*
     * 设置mapper层字段  角色状态
     */
    protected static final String  MODEL_AVAILABLE="available";
    /*
     * 设置mapper层字段  用户id
     */
    protected static final String  MODEL_USERID="userId";
    /*
     * 设置mapper层字段  角色名
     */
    protected static final String MODEL_ROLENAME="roleName";
    
    
    //默认查询返回的字段 id roleName systemType description available
    protected String[] defaultFields = {"roleId","roleName","systemType","description","available"};
    
    // 需要互相映射的字段("/"分隔,前面是resource字段名,后面是model字段名)
    private static final String[] transferField = {"roleId/id",};
    
    @Override
    protected String[] transferField()
    {
        return transferField;
    }
}
