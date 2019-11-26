package com.etoc.service.resource;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
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

import com.etoc.base.SysQueryService;
import com.etoc.constant.DataType;
import com.etoc.exception.ChannelException;
import com.etoc.service.ContextHolder;
import com.etoc.service.resource.impl.ResourceServiceFacade;
import com.etoc.service.resource.impl.SaveResource;
import com.etoc.service.resource.vo.Resource;
import com.etoc.service.resource.vo.SysResourceResource;
import com.etoc.vo.StateInstance;

/**
 * 
 * 资源管理接口
 * <功能详细描述>
 * 
 * @author  liuxiaolong
 * @version  [版本号, 2019年1月8日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@RestController
@RequestMapping("/resources")
public class ResourceController
{
    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    protected ContextHolder context;
    
    /**
      * 1.分页查询资源信息
     * 2.查询所用
     * 3.返回资源树
     * @param pageNum 当前页
     * @param pageSize每页的记录数
     * @param dataType返回的数据结构List,Page,Tree
     * @param fields需要返回的字段
     * @param name资源名
     * @param type资源类型
     * @param systemType平台类型
     * @param available状态
     * @param parentCode父类节点
     * @return
     * @see [类、类#方法、类#成员]
     */
    @GetMapping(value = "/", produces = {"application/json;charset=UTF-8"})
    /* @ResponseStatus(HttpStatus.OK)*/
    public ResponseEntity<?> search(Integer pageNum, Integer pageSize, DataType dataType, String[] fields, String name,
        String type, Integer systemType, String available, String parentCode)
    {
        logger.info(
            "开始进入资源多查询,parames为pageNum:{},pageSize:{},name:{},dataType:{},fields:{},type:{},systemType:{},available:{},parentCode:{}",
            pageNum,
            pageSize,
            name,
            dataType,
            type,
            systemType,
            available,
            parentCode);
        // 查询结果
        Object obj = null;
        try
        {
            ResourceService resourceService =
                context.getBean(ResourceServiceFacade.class).addName(name).addType(type).addSystemType(systemType)
                .addAvailable(available).addParentCode(parentCode);
            SysQueryService<?> baseService = resourceService.queryResource(pageNum, pageSize, dataType, fields);
            obj = baseService.resultObj();
        }
        catch (ChannelException e)
        {
           
            throw e;
        }
        catch (Exception e)
        {
            logger.error("查询资源信息失败", e);
            throw new ChannelException("查询资源信息失败", e);
        }
        logger.info("资源多查询结果为:{}", obj);
        return new ResponseEntity<Object>(obj, HttpStatus.OK);
    }
    
    /**
     * 根据资源id进行查询资源
     * 
     * @param resourceId资源id
     * @param field 需要返回前台 的字段
     * @return 资源信息
     * @see [类、类#方法、类#成员]
     */
    @GetMapping(value = "/{id}/", produces = {"application/json;charset=UTF-8"})
    /*  @ResponseStatus(HttpStatus.OK)*/
    public ResponseEntity<?> get(@PathVariable(name = "id", required = true) String id, String[] fields)
    {
        logger.info("开始根据id查询资源,parames为id:{},fields:{}", id, fields);
        Object sysResource = null;
        try
        {
            ResourceService resourceService = context.getBean(ResourceServiceFacade.class).addQueryId(id);
            SysQueryService<SysResourceResource> service = resourceService.queryResource(fields);
            sysResource = service.resultObj();
        }
        catch (ChannelException e)
        {
           
            throw e;
        }
        catch (Exception e)
        {
            logger.error("获取资源信息失败", e);
            throw new ChannelException("获取资源信息失败", e);
        }
        logger.info("根据id查询的结果为：{}", sysResource);
        return new ResponseEntity<Object>(sysResource, HttpStatus.OK);
    }
    
    /**
     * 根据id对资源信息修改
     * 
     * @param id 资源id
     * @param sysResource  将修改的信息    
     * @see [类、类#方法、类#成员]
     */
    @PatchMapping(value = "/{id}/")
    public ResponseEntity<?> modify(@PathVariable(value = "id") String id, @RequestBody Resource sysResource)
    {
        logger.info("开始根据id对资源信息修改,parames为id:{},sysResource:{}", id, sysResource);
        if (StringUtils.isEmpty(sysResource.getParentCodes()))
        {
            logger.error("父资源编码列表不能为空！");
            throw new ChannelException("父资源编码列表不能为空！");
        }
        try
        {
            ResourceService resourceService =
                context.getBean(ResourceServiceFacade.class).setObject(sysResource).addQueryId(id);
            resourceService.moveResource();
        }
        catch (ChannelException e)
        {
           
            throw e;
        }
        catch (Exception e)
        {
            logger.error("资源修改未成功！", e);
            throw new ChannelException("资源修改未成功！", e);
        }
        logger.info("资源修改完成！");
        return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
    }
    
    /**
    * 创建资源
    * <功能详细描述>
    * @param sysResource 新增入参对象
    * @return 新增成功的对象
    * @see [类、类#方法、类#成员]
    */
    @PostMapping("/")
    /* @ResponseStatus(HttpStatus.CREATED)*/
    public ResponseEntity<?> add(@RequestBody Resource sysResource)
    {
        logger.info("开始创建资源,sysResource:{}", sysResource);
        Object object = null;
        if (StringUtils.isEmpty(sysResource.getParentCode()))
        {
            logger.error("请选择父编码！");
            throw new ChannelException("请选择父编码！");
        }
        if (StringUtils.isEmpty(sysResource.getParentCodes()))
        {
            logger.error("父资源编码列表不能为空！");
            throw new ChannelException("父资源编码列表不能为空！");
        }
        try
        {
            ResourceServiceFacade resourceService = context.getBean(ResourceServiceFacade.class).setObject(sysResource);
            SaveResource service = resourceService.saveResource();
            object = service.resultObj();
            
        }
        catch (ChannelException e)
        {
           
            throw e;
        }
        catch (Exception e)
        {
            logger.error("创建的资源信息失败！", e);
            throw new ChannelException("创建的资源信息失败！", e);
        }
        logger.info("创建的资源信息完成！");
        return new ResponseEntity<Object>(object, HttpStatus.CREATED);
    }
    
    /**
     * 批量修改资源状态 暂停或恢复
     * available:激活;suspend:暂停;cancel:注销
     * @param states 枚举数组
     * @see [类、类#方法、类#成员]
     */
    @PatchMapping(value = "/status/")
    public ResponseEntity<?> batchResouceStatus(@RequestBody List<StateInstance> states)
    {
        logger.info("开始批量修改资源状态 暂停或恢复,states:{}", states);
        if (CollectionUtils.isEmpty(states))
        {
            logger.error("请输入批量修改的参数！");
            throw new ChannelException("请输入批量修改的参数！");
        }
        try
        {
            ResourceServiceFacade resourceService = context.getBean(ResourceServiceFacade.class);
            resourceService.moveResource(states);
        }
        catch (ChannelException e)
        {
           
            throw e;
        }
        catch (Exception e)
        {
            logger.error("批量修改资源状态失败!", e);
            throw new ChannelException("批量修改资源状态失败！", e);
        }
        logger.info("批量修改资完成!");
        return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
    }
}
