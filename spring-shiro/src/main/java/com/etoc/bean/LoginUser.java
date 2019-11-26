package com.etoc.bean;

public class LoginUser {
	private String username;
	private String password;
	private String vcode;
	private String vcodeKey;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getVcode() {
		return vcode;
	}

	public void setVcode(String vcode) {
		this.vcode = vcode;
	}

	public String getVcodeKey() {
		return vcodeKey;
	}

	public void setVcodeKey(String vcodeKey) {
		this.vcodeKey = vcodeKey;
	}
}
