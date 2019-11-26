package com.etoc.config;

import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;

/**
 * Created by Admin on 2019/4/17.
 */
@Configuration
public class MyCacheConfig {
    @Bean("myKeyGenerator")
    public KeyGenerator keyGenerator(){
      return   new KeyGenerator(){
          @Override
         public Object generate(Object var1, Method var2, Object... var3){
                   return  var2.getName()+"123";
          }
        };
    }
}
