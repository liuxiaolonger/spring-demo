package com.etoc.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import com.etoc.constant.DataType;

import feign.hystrix.FallbackFactory;
@Component
public class FeignClientFallBack implements FallbackFactory<FeignClientRemote>{

	@Override
	public FeignClientRemote create(Throwable arg0) {
		return new FeignClientRemote() {
			public ResponseEntity<?> search(@RequestParam("loginName")String loginName){
				 	return new ResponseEntity<Object>("服务异常", HttpStatus.OK);
			}
		};
	}

}
