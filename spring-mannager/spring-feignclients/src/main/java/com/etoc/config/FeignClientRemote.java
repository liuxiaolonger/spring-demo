package com.etoc.config;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "spring-api",fallbackFactory=FeignClientFallBack.class)
public interface FeignClientRemote {
	@GetMapping("/emp/{id}/")
	public ResponseEntity<?> get(@PathVariable("id") Integer id);
}
