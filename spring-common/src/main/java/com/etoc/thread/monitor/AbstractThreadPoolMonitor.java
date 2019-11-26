package com.etoc.thread.monitor;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * 抽象线程池监控服务
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  ETOC-ChenChao
 * @version  [版本号, Aug 20, 2018]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public abstract class AbstractThreadPoolMonitor<MONITOR_BEAN> implements IThreadPoolMonitor<MONITOR_BEAN> {
    /**
     * 线程池对象
     */
    protected ThreadPoolTaskExecutor executor;
    
    @Override
    public ThreadPoolTaskExecutor getExecutor() {
        return executor;
    }

    @Override
    public void setExecutor(ThreadPoolTaskExecutor executor) {
        this.executor = executor;
    }
    
}
