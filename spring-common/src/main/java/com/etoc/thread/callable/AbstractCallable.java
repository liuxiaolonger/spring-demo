package com.etoc.thread.callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * 线程处理的抽象类
 * 根据请求响应的对象，定义线程的处理模板
 * 
 * @author  Administrator
 * @version  [版本号, 2015-8-22]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public abstract class AbstractCallable<REQUEST_MSG> implements EtocCallable<REQUEST_MSG> {
	protected Logger logger = LoggerFactory.getLogger(getClass());
    /**
     *  泛型请求对象
     */
    protected REQUEST_MSG request;
    
    public AbstractCallable(REQUEST_MSG request) {
        this.request = request;
    }
    
    public REQUEST_MSG getRequest() {
        return request;
    }
    
    public void setRequest(REQUEST_MSG request) {
        this.request = request;
    }
    
}
