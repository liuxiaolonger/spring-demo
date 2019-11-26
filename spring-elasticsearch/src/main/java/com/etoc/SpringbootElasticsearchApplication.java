package com.etoc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * springboot默认支持和两种技术和ES交互
 * 1.jest
 * 2.Elasticsearch
 */
@SpringBootApplication
public class SpringbootElasticsearchApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootElasticsearchApplication.class, args);
	}

}
