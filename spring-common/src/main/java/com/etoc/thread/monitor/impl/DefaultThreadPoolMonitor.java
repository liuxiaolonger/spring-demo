package com.etoc.thread.monitor.impl;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import com.etoc.thread.monitor.AbstractThreadPoolMonitor;
import com.etoc.thread.monitor.bean.ThreadMonitorBean;

/**
 * 默认线程监控程序
 * 注入线程池对象  executor,
 * 完成定时打印线程池数据
 * 
 * @author  Administrator
 * @version  [版本号, 2015-8-23]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class DefaultThreadPoolMonitor extends AbstractThreadPoolMonitor<ThreadMonitorBean> 
	implements InitializingBean, DisposableBean, Runnable {
    private static Logger logger = LoggerFactory.getLogger(DefaultThreadPoolMonitor.class);
    
    /**
     * 线程睡眠时间，单位秒
     */
    private int monitoringPeriod;
    
    /**
     * 线程池监控线程运行标志
     */
    private static boolean flag = true;
    
    /**
     * 主要打印线程池信息
     * <功能详细描述>
     * @see [类、类#方法、类#成员]
     */
    public void action() {
        ThreadMonitorBean bean = execute();
        logger.info(bean.toString());
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public ThreadMonitorBean execute() {
        ThreadMonitorBean bean = new ThreadMonitorBean();
        if (executor != null && !executor.getThreadPoolExecutor().isShutdown()) {
            ThreadPoolExecutor poolExecutor = executor.getThreadPoolExecutor();
            bean.setActiveCount(poolExecutor.getActiveCount());
            bean.setCompletedTaskCount(poolExecutor.getCompletedTaskCount());
            bean.setCorePoolSize(poolExecutor.getCorePoolSize());
            bean.setIsTerminated(poolExecutor.isTerminated());
            bean.setKeepAliveTime(poolExecutor.getKeepAliveTime(TimeUnit.SECONDS));
            bean.setLargestPoolSize(poolExecutor.getLargestPoolSize());
            bean.setMaximumPoolSize(poolExecutor.getMaximumPoolSize());
            bean.setPoolSize(poolExecutor.getPoolSize());
            bean.setTaskCount(poolExecutor.getTaskCount());
            if(poolExecutor.getQueue() != null){
            	bean.setQueueSize(poolExecutor.getQueue().size());
            }
        }
        return bean;
    }
    
    @Override
    public void destroy() throws Exception {
        flag = false;
        logger.info("monitor thread dead");
    }
    
    @Override
    public void afterPropertiesSet() throws Exception {
        Thread thread = new Thread(this, "ThreadPoolMonitor");
        thread.start();
        logger.info("monitor thread runnable");
    }
    
    @Override
    public void run() {
        try {
            while (flag) {
            	action();
                Thread.sleep(monitoringPeriod * 1000);
            }
        } catch (Exception e) {
            logger.error("线程池监控器运行出错", e);
        }
        
    }
    
    public int getMonitoringPeriod() {
        return monitoringPeriod;
    }
    
    public void setMonitoringPeriod(int monitoringPeriod) {
        this.monitoringPeriod = monitoringPeriod;
    }
    
}
