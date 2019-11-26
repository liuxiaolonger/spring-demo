package com.etoc.service;


import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * Created by Admin on 2019/4/25.
 */
@Component
public class UserService {
    @Reference
    TicketService service;

    public void saySomething(){
        System.out.println(service.buyOne());
    }
}
