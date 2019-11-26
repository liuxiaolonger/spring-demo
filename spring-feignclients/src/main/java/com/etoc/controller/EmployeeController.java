package com.etoc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.etoc.ContextHolder;
import com.etoc.config.FeignClientRemote;

@RestController
public class EmployeeController {
     @Autowired
     private ContextHolder context;

	@GetMapping("/emp/{id}/")
	public ResponseEntity<?> get(@PathVariable("id") Integer id) {
		FeignClientRemote f=context.getBean(FeignClientRemote.class);
		return  f.get(id)   ;
	}
}
