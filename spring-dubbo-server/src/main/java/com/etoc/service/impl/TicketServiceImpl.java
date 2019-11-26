package com.etoc.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.etoc.service.TicketService;
import org.springframework.stereotype.Component;


/**
 * Created by Admin on 2019/4/25.
 */
@Component
@Service//dubbo注解
public class TicketServiceImpl implements TicketService {
    @Override
    public String buyOne() {
        return "付出者联盟4";
    }
}
