package com.etoc.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import com.netflix.loadbalancer.RetryRule;
import com.netflix.loadbalancer.RoundRobinRule;

@Configuration
public class ConfigBean {
	@Bean
	@LoadBalanced // spring cloud ribbon是基于netflix ribbon实现的一套客户端 负载均衡的工具
	public RestTemplate getTemplate() {
		return new RestTemplate();
	}
    //ribbon的算法
	@Bean
	public IRule myRule() {
		return new RetryRule();
	}
}
