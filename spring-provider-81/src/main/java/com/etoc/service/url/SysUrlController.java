package com.etoc.service.url;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.etoc.base.SysQueryService;
import com.etoc.constant.DataType;
import com.etoc.exception.ChannelException;
import com.etoc.service.ContextHolder;
import com.etoc.service.url.impl.SaveUrl;
import com.etoc.service.url.impl.UrlServiceFacade;
import com.etoc.service.url.vo.Url;

/**
 * 动态url权限控制 
 * <功能详细描述>
 * @author  liuxiaolong
 * @version  [版本号, 2019年1月4日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@RestController
@RequestMapping("/urls")
public class SysUrlController
{
    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    protected ContextHolder context;
    
    /**
    * 分页查询
    * <功能详细描述>
    * @param pageNum当前页
    * @param pageSize每页的记录数
    * @param name 查询条件   
    * @param dataType  需要返回的数据结构,类如 List Page  Tree
    * @param fields 需要返回数据的字段
    * @return
    * @see [类、类#方法、类#成员]
    */
    @GetMapping(value = "/", produces = {"application/json;charset=UTF-8"})
   /* @ResponseStatus(HttpStatus.OK)*/
    public ResponseEntity<?> searchSysUrl(
    		@RequestParam(value = "pageNum", required = true) Integer pageNum,
    		@RequestParam(value = "pageSize", required = true) Integer pageSize, String name, DataType dataType,
        String[] fields)
    {
        logger.info("开始查询url权限的信息 ,parames为pageNum:{},pageSize:{},name:{},dataType:{},fields:{}",
            pageNum,
            pageSize,
            name,
            dataType,
            fields);
        UrlService urlService = context.getBean(UrlServiceFacade.class).addQueryName(name);
        // 查询结果
        Object obj = null;
        try
        {
            SysQueryService<?> baseService = urlService.queryUrl(pageNum, pageSize, dataType, fields);
            obj = baseService.resultObj();
        }
        catch (ChannelException e)
        {
           
            throw e;
        }
        catch (Exception e)
        {
            logger.error("查询url未成功！", e);
            throw new ChannelException("查询url未成功！", e);
        }
        logger.info("查询结果为:{}", obj);
        return new ResponseEntity<Object>(obj, HttpStatus.OK);
    }
    
    /**
    * 新增动态url权限的信息 
    * <功能详细描述>
    * @param sysUrlFilter 将要修改的url信息
    * @return
    * @see [类、类#方法、类#成员]
    */
    @PostMapping(value = "/")
    /* @ResponseStatus(HttpStatus.CREATED)*/
    public ResponseEntity<?> add(@RequestBody Url sysUrlFilter)
    {
        logger.info("新增动态url权限的信息,parames:{} ", sysUrlFilter);
        Object object = null;
        UrlService urlService = context.getBean(UrlServiceFacade.class).setObject(sysUrlFilter);
        try
        {
            SaveUrl saveUrl = urlService.saveUrl();
            object = saveUrl.resultObj();
        }
        catch (ChannelException e)
        {
           
            throw e;
        }
        catch (Exception e)
        {
            logger.error("添加url未成功！", e);
            throw new ChannelException("添加url未成功！", e);
        }
        logger.info("新增的url:{}", object);
        return new ResponseEntity<Object>(object, HttpStatus.CREATED);
    }
    
    /**
    *根据ID查询动态url权限的信息
    * <功能详细描述>
    * @param id url的主键id
    * @param fields 需要返回的字段
    * @return
    * @see [类、类#方法、类#成员]
    */
    @GetMapping(value = "/{id}/", produces = {"application/json;charset=UTF-8"})
  /*  @ResponseStatus(HttpStatus.OK)*/
    public ResponseEntity<?> getBySysUrlId(@PathVariable(value = "id", required = true) String id, String[] fields)
    {
        logger.info("根据ID查询动态url权限的信息,parames为id:{},fields:{}", id, fields);
        Object sysUrlFilter = null;
        try
        {
            UrlService urlService = context.getBean(UrlServiceFacade.class).addQueryId(id);
            sysUrlFilter = urlService.queryUrl(fields).resultObj();
        }
        catch (ChannelException e)
        {
           
            throw e;
        }
        catch (Exception e)
        {
            logger.error("查询url失败!", e);
            throw new ChannelException("查询url失败", e);
        }
        logger.info("查询结果为：{}", sysUrlFilter);
        return new ResponseEntity<Object>(sysUrlFilter, HttpStatus.OK);
    }
    
    /**
    * 保存修改后的动态url权限的信息 
    * <功能详细描述>
    * @param id--------url的主键id
    * @param sysUrlFilter --------url对象
    * @return
    * @see [类、类#方法、类#成员]
    */
    @PatchMapping(value = "/{id}/")
    public ResponseEntity<?> modifySysUrl(@PathVariable(value = "id", required = true) String id,@RequestBody Url sysUrlFilter)
    {
        logger.info("保存修改后的动态url权限的信息 ,parames为id:{},sysUrlFilter:{}", id, sysUrlFilter);
        try
        {
            UrlService urlService = context.getBean(UrlServiceFacade.class).setObject(sysUrlFilter).addQueryId(id);
            urlService.moveUrl();
        }
        catch (ChannelException e)
        {
           
            throw e;
        }
        catch (Exception e)
        {
            logger.error("修改url未成功！", e);
            throw new ChannelException("修改url未成功！", e);
        }
        logger.info("修改url完成！");
        return new ResponseEntity<Object>( HttpStatus.NO_CONTENT);
    }
    
    /**
     * 删除动态url权限的信息 单条
     * <功能详细描述>
     * @param id--------url的主键id
     * @return
     * @see [类、类#方法、类#成员]
     */
    @DeleteMapping(value = "/{id}/")
   /* @ResponseStatus(HttpStatus.NO_CONTENT)*/
    public ResponseEntity<?> removeSysUrl(@PathVariable(name = "id", required = true) String id)
    {
        logger.info("删除动态url权限的信息 ,id:{}", id);
        try
        {
            UrlService urlService = context.getBean(UrlServiceFacade.class).addQueryId(id);
            urlService.removeUrl();
        }
        catch (ChannelException e)
        {
           
            throw e;
        }
        catch (Exception e)
        {
            logger.error("删除动态url权限的信息未成功！", e);
            throw new ChannelException("删除url未成功！", e);
        }
        logger.info("删除动态url权限的信息完成！");
        return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
    }
}
