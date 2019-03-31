package com.etoc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.etoc.service.EmployeeService;

@RestController
public class EmployeeController {
	@Autowired
	private EmployeeService service;

	@GetMapping("/emp/{id}/")
	public ResponseEntity<?> get(@PathVariable("id") Integer id) {
		return new ResponseEntity<Object>(service.selectByPrimaryKey(id), HttpStatus.OK);
	}
}
