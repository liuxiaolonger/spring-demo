package com.etoc.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class User {
	public static String username;
	
     @Value("${user.get.username}")
	public  void setUsername(String username) {
		User.username = username;
     }
}
