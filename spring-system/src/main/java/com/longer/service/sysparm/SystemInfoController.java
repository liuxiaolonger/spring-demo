/**
 * 
 */
package com.longer.service.sysparm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.etoc.constant.DataType;
import com.etoc.exception.ChannelException;
import com.longer.ContextHolder;
import com.longer.base.SysQueryService;
import com.longer.base.SysSaveService;
import com.longer.service.sysparm.impl.SystemServiceFacade;
import com.longer.service.sysparm.vo.SystemInfo;
import com.longer.service.sysparm.vo.SystemInfoResource;

/**
 * 
 * 系统参数管理
 * <功能详细描述>
 * 
 * @author  chuyh
 * @version  [版本号, 2019年1月9日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@RestController("params")
@RequestMapping("/params")
public class SystemInfoController
{
    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    protected ContextHolder context;
    
    /**
     * 1.分页查询系统参数信息
     * 2.根据键查询系统参数信息
     * 3.根据值查询系统参数信息
     * 4.根据键和值查询系统参数信息
     * 5.根据名称和键查询系统参数信息
     * 6.修改时通过键+名+id查看是否名重复
     * 7.修改时通过键+值+id查看是否值重复
     * <功能详细描述>
     * @param pageNum 当前页
     * @param pageSize 每页数量
     * @param dataType 响应数据类型:List Page Tree
     * @param fields 需要响应的数据字段
     * @param systemName 系统参数名称
     * @param systemKey  系统参数键名称
     * @param systemValue  系统参数值名称
     * @return
     * @see [类、类#方法、类#成员]
     */
    @GetMapping(value = "/", produces = {"application/json;charset=UTF-8"})
    // @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> searchSystem(Integer pageNum, Integer pageSize, DataType dataType, String[] fields,
        String systemId, String systemName, String systemKey, String systemValue)
    {
        logger.info(
            "开始进入系统参数多查询,param为, pageNum= {} ,pageSize= {} ,dataType={} ,fields= {},systemId= {},systemName= {} ,systemKey= {} ,systemValue= {} ",
            pageNum,
            pageSize,
            dataType,
            fields,
            systemId,
            systemName,
            systemKey,
            systemValue);
        // 查询结果
        SysQueryService<?> sysQueryService = null;
        Object object = null;
        try
        {
            SystemService systemService = context.getBean(SystemServiceFacade.class)
                .addQueryId(systemId)
                .addQuerySystemKey(systemKey)
                .addQuerySystemName(systemName)
                .addQuerySystemValue(systemValue);
            sysQueryService = systemService.querySystem(pageNum, pageSize, dataType, fields);
            object = sysQueryService.resultObj();
        }
        catch (ChannelException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            logger.error("查询系统参数失败", e);
            throw new ChannelException("查询系统参数失败", e);
        }
        logger.info("查询结果为:" + object);
        //return object;\
        return new ResponseEntity<Object>(object, HttpStatus.OK);
    }
    
    /**
     * 根据id查询系统参数信息
     * 
     * @param id------------系统参数主键id
     * @return
     * @see [类、类#方法、类#成员]
     */
    @GetMapping(value = "/{id}/", produces = {"application/json;charset=UTF-8"})
    //@ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> getBySystemId(@PathVariable(name = "id", required = true) String id, String[] fields)
    {
        logger.info("开始进入系统参数单查询,param为, systemId= {} ", id);
        // 查询结果
        SysQueryService<SystemInfoResource> sysQueryService = null;
        Object object = null;
        try
        {
            SystemService systemService = context.getBean(SystemServiceFacade.class).addQueryId(id);
            sysQueryService = systemService.querySystem(fields);
            object = sysQueryService.resultObj();
        }
        catch (ChannelException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            logger.error("查询系统参数失败", e);
            throw new ChannelException("查询系统参数失败", e);
        }
        logger.info("查询结果为:" + object);
        //return object;
        
        return new ResponseEntity<Object>(object, HttpStatus.OK);
    }
    
    /**
     * 保存修改后的系统参数信息
     * @param systemInfo ------------系统参数对象
     * @see [类、类#方法、类#成员]
     */
    @PatchMapping(value = "/{id}/")
    // @SysLog("保存修改后的系统参数信息")
    public ResponseEntity<?> modifySystemInfo(@PathVariable(name = "id", required = true) String id,
        @RequestBody SystemInfo systemInfo)
    {
        logger.info("开始进入系统参数修改,param为, systemInfo= {} ", systemInfo);
        // 查询结果
        try
        {
            SystemService systemService =
                context.getBean(SystemServiceFacade.class).setObject(systemInfo).addQueryId(id);
            systemService.moveSystem();
        }
        catch (ChannelException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            logger.error("查询系统参数失败", e);
            throw new ChannelException("查询系统参数失败", e);
        }
        logger.info("修改完成!!!");
        return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
    }
    
    /**
     * 新增系统参数信息
     * @param systemInfo-------------系统参数对象
     * @see [类、类#方法、类#成员]
     */
    @PostMapping(value = "/")
    // @ResponseStatus(HttpStatus.CREATED)
    // @SysLog("新增系统参数信息 ")
    public ResponseEntity<Object> addSystemInfo(@RequestBody SystemInfo systemInfo)
    {
        logger.info("开始进入系统参数增加,param为: {} ", systemInfo);
        // 新增
        SysSaveService<?> sysSaveService = null;
        Object result = null;
        try
        {
            SystemService systemService = context.getBean(SystemServiceFacade.class).setObject(systemInfo);
            sysSaveService = systemService.saveSystem();
            result = sysSaveService.resultObj();
        }
        catch (ChannelException channelExceptione)
        {
            throw channelExceptione;
        }
        catch (Exception e)
        {
            logger.error("新增加系统参数失败", e);
            throw new ChannelException("新增加系统参数失败", e);
        }
        logger.info("新增完成!!!");
        //return result;
        return new ResponseEntity<Object>(result, HttpStatus.CREATED);
    }
    
}
