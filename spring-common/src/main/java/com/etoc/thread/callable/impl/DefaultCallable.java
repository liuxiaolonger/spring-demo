package com.etoc.thread.callable.impl;

import java.util.Random;

import com.etoc.thread.callable.AbstractCallable;
import com.etoc.util.UUIDUtil;

/**
 * 回调线程默认实现
 * 
 * @author  ETOC-ChenChao
 * @version  [版本号, Aug 20, 2018]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class DefaultCallable extends AbstractCallable<String> {

    Random random = new Random();
    
    public DefaultCallable(String request) {
        super(request);
    }

    @Override
    public String call() throws Exception {
        String uuid = UUIDUtil.getUUID();
        Integer sleepMillis = random.nextInt(10000);
        Thread.sleep(sleepMillis);
        return "DefaultCallable.call() response: uuid=" + uuid + ", sleepTime =" + sleepMillis;
    }

    @Override
    public void onException() {
        
    }
    
}
