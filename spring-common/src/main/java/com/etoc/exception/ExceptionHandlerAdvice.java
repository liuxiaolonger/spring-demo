/*
 * 文 件 名:  ExceptionHandlerAdvice.java
 * 版    权:  ETOC 信息技术有限公司, Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  ETOC-ChenChao
 * 修改时间:  Apr 28, 2018
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.etoc.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.etoc.vo.ErrorInfo;


/**
 * 控制层异常映射
 * <功能详细描述>
 * 
 * @author  ETOC-ChenChao
 * @version  [版本号, Apr 28, 2018]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@ControllerAdvice
@Component("ExceptionHandlerAdvice")// 实例名称和org.activiti.rest.exception.ExceptionHandlerAdvice冲突
public class ExceptionHandlerAdvice {
	private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandlerAdvice.class);
	
	@ResponseStatus(HttpStatus.NOT_FOUND)  // 404
	@ExceptionHandler(ChannelObjectNotFoundException.class)
	@ResponseBody
	public ErrorInfo handleNotFound(ChannelObjectNotFoundException e) {
		return new ErrorInfo("查询不到数据", e);
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST) // 400
	@ExceptionHandler(HttpMessageConversionException.class)
	@ResponseBody
	public ErrorInfo handleBadMessageConversion(HttpMessageConversionException e) {
		return new ErrorInfo("请求参数格式错误", e);
	}
	
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)  // 500
	@ExceptionHandler(Exception.class)
	@ResponseBody
	public ErrorInfo handleOtherException(Exception e) {
		LOGGER.error("系统异常", e);
		return new ErrorInfo("服务内部错误", e);
	}
	
	@ResponseStatus(HttpStatus.NOT_IMPLEMENTED)  // 501
	@ExceptionHandler(ChannelException.class)
	@ResponseBody
	public ErrorInfo handleOtherException(ChannelException e) {
		LOGGER.error("业务异常", e);
		return new ErrorInfo(e.getMessage(), e);
	}
}
