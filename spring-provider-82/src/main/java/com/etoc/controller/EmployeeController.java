package com.etoc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.etoc.model.Employee;
import com.etoc.service.EmployeeService;

@RestController
@RefreshScope
public class EmployeeController {
    @Value("${user.get.username}")
	private  String username;
	@Autowired
	private EmployeeService service;
	@GetMapping("/emp/")
	public ResponseEntity<?> getAll() {
		return new ResponseEntity<Object>(service.selectAll(), HttpStatus.OK);
	}
	@GetMapping("/emp/{id}/")
	public ResponseEntity<?> get(@PathVariable("id") Integer id) {
		 Employee emp=service.selectByPrimaryKey(id);
		 emp.setEmail(username);
		return new ResponseEntity<Object>(emp, HttpStatus.OK);
	}
}
