package com.etoc.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 错误信息
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  ETOC-ChenChao
 * @version  [版本号, Apr 28, 2018]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ErrorInfo {

	  private String message;
	  private String exception;

	  public ErrorInfo(String message, Exception ex) {
	      this.message = message;
	      if (ex != null) {
	        this.exception = ex.getLocalizedMessage();
	      }
	  }

	  public String getMessage() {
	      return message;
	  }

	  public void setMessage(String message) {
	      this.message = message;
	  }
	  
	  public void setException(String exception) {
	      this.exception = exception;
	  }
	  
	  @JsonInclude(Include.NON_NULL)
	  public String getException() {
	      return exception;
	  }
}
