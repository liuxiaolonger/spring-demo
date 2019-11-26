package com.etoc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@RestController
public class UserController {
	
	//private final static String url = "http://127.0.0.1:8003";
	private final static String url = "http://SPRING-API";//用到eureka中的服务不用考虑端口和ip
	@Autowired
	private RestTemplate template;

	@GetMapping("/emp/{id}/")
	public String get(@PathVariable("id") Integer id) {
		System.err.println(url+"/emp/"+id+"/");
		return template.getForObject(url+"/emp/"+id+"/",String.class);
	}
}
