package com.etoc.config;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "spring-api",fallbackFactory=FeignClientFallBack.class)
public interface FeignClientRemote {
	@GetMapping(value = "/", produces = { "application/json;charset=UTF-8" })
	public ResponseEntity<?> search(@RequestParam("loginName")String loginName);
	
	
	
	
}
