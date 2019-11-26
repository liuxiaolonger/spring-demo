package com.longer;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ContextHolder implements ApplicationContextAware, BeanFactoryAware
{

	static ContextHolder context;
	
    BeanFactory beanFactory;
    
    ApplicationContext applicationContext;
    
    @Override
    public void setBeanFactory(BeanFactory beanFactory)
        throws BeansException
    {
        // TODO Auto-generated method stub
        this.beanFactory = beanFactory;
        context = this;
    }
    
    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
        throws BeansException
    {
        // TODO Auto-generated method stub
    	this.applicationContext = applicationContext;
        
    }
    
    public BeanFactory getBeanFactory()
    {
        return beanFactory;
    }
    
    public Object getService(String servName) {
        return beanFactory.getBean(servName);
    }
    
    public Object getBeanByName(String beanName) {  
        if (applicationContext == null){  
            return null;  
        }  
        return applicationContext.getBean(beanName);  
    }  
    
    public Object getBeanByName(String beanName,Object... obj) {  
        if (applicationContext == null){  
            return null;  
        }  
        return applicationContext.getBean(beanName,obj);  
    }
    
    public <T> T getBean(Class<T> type, Object... obj) {  
        return applicationContext.getBean(type, obj);  
    } 
    
    public <T> T getBean(String name, Class<T> type) {  
        return applicationContext.getBean(name, type);  
    } 

    public <T> T getBean(String name, Class<T> type, Object... obj) {  
        return applicationContext.getBean(name, type);  
    } 

    public Object getBean(String name, Object... obj) {  
        return applicationContext.getBean(name, obj);  
    } 
    
    
    
    
    
}
