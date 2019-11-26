package com.etoc.controller;

import com.etoc.service.AsynService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Admin on 2019/4/23.
 */
@RestController
public class AsynController {
    @Autowired
    private AsynService service;

    @GetMapping("/hello")
    public String  show() throws InterruptedException {
        service.hello();
        return "success";
    }
}
