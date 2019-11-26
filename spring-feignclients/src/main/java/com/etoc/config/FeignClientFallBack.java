package com.etoc.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import feign.hystrix.FallbackFactory;
@Component
public class FeignClientFallBack implements FallbackFactory<FeignClientRemote>{

	@Override
	public FeignClientRemote create(Throwable arg0) {
		return new FeignClientRemote() {
			
			@Override
			public ResponseEntity<?> get(Integer id) {	
				 	return new ResponseEntity<Object>("服务异常", HttpStatus.OK);
			}
		};
	}

}
