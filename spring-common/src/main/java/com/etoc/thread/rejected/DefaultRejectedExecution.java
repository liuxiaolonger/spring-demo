package com.etoc.thread.rejected;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.etoc.thread.future.EtocFuture;

/**
 * 
 * 处理线程池处理失败的任务
 * 创建线程池并且提交任务失败时，线程池会回调RejectedExecutionHandler接口的
 * 
 * 
 * @author  陈智
 * @version  [版本号, 2015-8-22]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class DefaultRejectedExecution implements RejectedExecutionHandler {

	private static Logger logger = LoggerFactory.getLogger(DefaultRejectedExecution.class);
	
	/**
	 * 失败线程回调方法
	 */
	@Override
    public void rejectedExecution(Runnable runnable, ThreadPoolExecutor executor) {
	    // 自定义抽象FutureTask线程，回调异常处理方法
		if (runnable instanceof EtocFuture) {
			EtocFuture<?> task  = (EtocFuture<?>) runnable;
			task.onException();
		}
		logger.error(runnable.toString() + "被线程池执行异常");
    }

}
