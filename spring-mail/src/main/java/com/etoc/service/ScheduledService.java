package com.etoc.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * Created by Admin on 2019/4/23.
 */
@Service
public class ScheduledService {
//    @Scheduled(cron = "0,1,2,3,4 * 22 * * ? ")//枚举
//    @Scheduled(cron = "0-4 * 22 * * ? ")//区间
    @Scheduled(cron = "0/4 * 22 * * ? ")//四秒一次
    public void hello(){
        System.out.println("开启定时任务》》》》");
    }
}
