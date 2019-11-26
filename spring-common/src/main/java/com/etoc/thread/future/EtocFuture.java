package com.etoc.thread.future;

import java.util.concurrent.Future;

/**
 * 
 * 特性线程接口
 * <功能详细描述>
 * 
 * @author  ETOC-ChenChao
 * @version  [版本号, Aug 20, 2018]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface EtocFuture<V> extends Future<V>, Runnable {
	/**
	 * 线程异常处理
	 * <功能详细描述>
	 * @see [类、类#方法、类#成员]
	 */
    void onException();
}
