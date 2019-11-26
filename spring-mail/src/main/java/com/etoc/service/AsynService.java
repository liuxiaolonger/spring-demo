package com.etoc.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * Created by Admin on 2019/4/23.
 */
@Service
public class AsynService {

    @Async //异步注解
    public void  hello() throws InterruptedException {
        Thread.sleep(3000);
        System.out.println("处理数据中....");
    }
}
