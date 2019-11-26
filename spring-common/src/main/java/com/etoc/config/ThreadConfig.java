/*
 * 文 件 名:  ThreadConfig.java
 * 版    权:  ETOC 信息技术有限公司, Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  hzy
 * 修改时间:  2018年8月13日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */

package com.etoc.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.etoc.thread.monitor.impl.DefaultThreadPoolMonitor;
import com.etoc.thread.rejected.DefaultRejectedExecution;

/**
 * 线程池配置 <功能详细描述>
 * 
 * @author liuchangsong
 * @version [版本号, 2018年8月13日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
//@Configuration
public class ThreadConfig {

//	@Value("${thread.corePoolSize:5}")
//	private int corePoolSize;
//
//	@Value("${thread.maxPoolSize:10}")
//	private int maxPoolSize;
//
//	@Value("${thread.queueCapacity:10000}")
//	private int queueCapacity;
//
//	@Value("${thread.keepAliveSeconds:30}")
//	private int keepAliveSeconds;
//
//	@Value("${thread.allowCoreThreadTimeOut:true}")
//	private boolean allowCoreThreadTimeOut;
//
//	@Value("${thread.waitForJobsToCompleteOnShutdown:true}")
//	private boolean waitForJobsToCompleteOnShutdown;
//
//	@Bean
//	public DefaultRejectedExecution rejectedExecutionHandler() {
//		return new DefaultRejectedExecution();
//	}
//
//	@Bean
//	public ThreadPoolTaskExecutor threadPoolTaskExecutor(DefaultRejectedExecution rejectedExecutionHandler) {
//		ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
//		threadPoolTaskExecutor.setCorePoolSize(corePoolSize);
//		threadPoolTaskExecutor.setMaxPoolSize(maxPoolSize);
//		threadPoolTaskExecutor.setQueueCapacity(queueCapacity);
//		threadPoolTaskExecutor.setKeepAliveSeconds(keepAliveSeconds);
//		threadPoolTaskExecutor.setAllowCoreThreadTimeOut(allowCoreThreadTimeOut);
//		threadPoolTaskExecutor.setWaitForTasksToCompleteOnShutdown(waitForJobsToCompleteOnShutdown);
//		threadPoolTaskExecutor.setRejectedExecutionHandler(rejectedExecutionHandler);
//		return threadPoolTaskExecutor;
//	}
//
//	@Bean
//	public DefaultThreadPoolMonitor threadPoolMonitor(ThreadPoolTaskExecutor threadPoolTaskExecutor) {
//		DefaultThreadPoolMonitor threadPoolMonitor = new DefaultThreadPoolMonitor();
//		threadPoolMonitor.setExecutor(threadPoolTaskExecutor);
//		threadPoolMonitor.setMonitoringPeriod(60);// 一分钟检测一次
//		return threadPoolMonitor;
//	}
}
