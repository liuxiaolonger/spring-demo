package com.etoc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.etoc.service.EmployeeService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
public class EmployeeController {
	@Autowired
	private EmployeeService service;

	@GetMapping("/emp/{id}/")
	@HystrixCommand(fallbackMethod="hystrixMethod")
	public ResponseEntity<?> get(@PathVariable("id") Integer id) {
		if(null==service.selectByPrimaryKey(id)) {
			throw new RuntimeException("没查到数据");
		}
		return new ResponseEntity<Object>(service.selectByPrimaryKey(id), HttpStatus.OK);
	}
	
	public ResponseEntity<?> hystrixMethod(@PathVariable("id") Integer id) {
		
		return new ResponseEntity<Object>("找不到该员工", HttpStatus.OK);
	}
}
