package com.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.netflix.loadbalancer.IRule;

@Configuration
public class MyselfRule  {
	@Bean
	public IRule myRule() {
		return new MyRule();
	}
}
