package com.longer.springmvc.controller;

import com.longer.springmvc.model.Employee;
import com.longer.springmvc.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeController {
    @Autowired
    private EmployeeService service;

    @GetMapping("/emp/{id}/")
    public ResponseEntity<?> get(@PathVariable("id") Integer id) {
        Employee emp = service.selectByPrimaryKey(id);
        return new ResponseEntity<Object>(emp, HttpStatus.OK);
    }
}
