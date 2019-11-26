package com.etoc.shiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * 
 * @author Admin
 *
 */

public class JWTToken implements AuthenticationToken {
	public JWTToken(String token) {
        this.token = token;
    }
    private String token;

    public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
