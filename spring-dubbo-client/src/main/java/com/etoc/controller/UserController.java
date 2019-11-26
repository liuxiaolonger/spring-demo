package com.etoc.controller;

import com.etoc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Admin on 2019/4/26.
 */
@RestController
public class UserController {
    @Autowired
    UserService service;
    @GetMapping("/hello")
    public void test1() {
        service.saySomething();
    }

}
