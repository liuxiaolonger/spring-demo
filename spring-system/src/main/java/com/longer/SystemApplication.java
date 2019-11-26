package com.longer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
 * @EnableFeignClients 跨项目调用shiro-server的注解@FeignClient需要这个 
 * 表示支持Get方法中通过@RequestParam指定需要传递的参数
 *
 */
//@EnableDiscoveryClient
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
//@EnableFeignClients(basePackages = "channel.shiro.rest.remote")
@ComponentScan(basePackages = {"com.longer", "com.etoc"})
public class SystemApplication
{
    public static void main(String[] args)
    {
        ConfigurableApplicationContext context = SpringApplication.run(SystemApplication.class, args);
        
    }
}
