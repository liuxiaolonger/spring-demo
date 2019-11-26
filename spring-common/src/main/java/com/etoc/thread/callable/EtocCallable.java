package com.etoc.thread.callable;

import java.util.concurrent.Callable;

/**
 * 
 * 可回调线程接口
 * <功能详细描述>
 * 
 * @author  ETOC-ChenChao
 * @version  [版本号, Aug 20, 2018]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface EtocCallable<REQUEST_MSG> extends Callable<REQUEST_MSG> {
    /**
     * 线程执行失败的回调方法
     * <br/>包括：投递失败，未执行线程流程
     * @see [类、类#方法、类#成员]
     */
    void onException();
    
    /**
     * 获取请求数据
     * <功能详细描述>
     * @return
     * @see [类、类#方法、类#成员]
     */
    REQUEST_MSG getRequest();
    
    /**
     * 设置请求数据
     * <功能详细描述>
     * @param request
     * @see [类、类#方法、类#成员]
     */
    void setRequest(REQUEST_MSG request);
}
