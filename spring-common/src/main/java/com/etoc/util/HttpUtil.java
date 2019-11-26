package com.etoc.util;

import java.io.IOException;
import java.net.URI;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * httpClient工具类封装
 * @author 陈智
 *
 */
public final class HttpUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);
	
	/**
	 * HTTP连接超时
	 */
	public static final int CONNECT_TIMEOUT = 20000;
	
	/**
	 * HTTP传输超时
	 */
	public static final int SOCKET_TIMEOUT = 60000;
	
	private HttpUtil() {
		super();
	}

	/**
	 * 发送HTTP~GET请求
	 * <功能详细描述>
	 * @param url 请求路径，包含查询参数
	 * @param headers 请求头
	 * @param charset 响应字符集处理
	 * @return 响应结果
	 * @see [类、类#方法、类#成员]
	 */
	public static String httpGet(String url, Map<String, String> headers, String charset) {
		String result = "";
		CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
        	URI uri = new URI(url);
            HttpGet httpGet = new HttpGet(uri);
            logger.info("发送的HTTP~GET请求报文:{}", uri.getQuery());
            // 设置HTTP请求配置
            RequestConfig config = RequestConfig.custom()
            		.setConnectTimeout(CONNECT_TIMEOUT)// 连接超时
            		.setSocketTimeout(SOCKET_TIMEOUT)// 传输超时
            		.build();
            httpGet.setConfig(config);
            // 设置HTTP请求头
            if(headers != null && headers.size() > 0) {
            	for(Entry<String, String> header : headers.entrySet()) {
            		httpGet.setHeader(header.getKey(),header.getValue());
            	}
            }
            // 发送请求
            CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
            // 处理响应
            HttpEntity httpResponseEntity = httpResponse.getEntity();
            result = new String(EntityUtils.toByteArray(httpResponseEntity), charset);
            return result;
        } catch (Exception e) {
            logger.error("处理HTTP~GET异常:" + e.getMessage());
            throw new RuntimeException(e);
        } finally {
        	logger.info("收到的HTTP~GET响应报文:{}", result);
            try {
                httpClient.close();
            } catch (IOException e) {
                logger.error("关闭httpClient异常!", e);
            }
        }
	}
	
	/**
	 * 发送HTTP~POST请求
	 * <功能详细描述>
	 * @param url 请求路径
	 * @param content 请求内容
	 * @param headers 请求头
	 * @param charset 响应字符集处理
	 * @return 响应结果
	 * @see [类、类#方法、类#成员]
	 */
	public static String httpPost(String url, String content, Map<String, String> headers, String charset) {
		String result = "";
		CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
        	URI uri = new URI(url);
            HttpPost httpPost = new HttpPost(uri);
            logger.info("发送的HTTP~POST请求报文:{}", content);
            // 设置HTTP请求配置
            RequestConfig config = RequestConfig.custom()
            		.setConnectTimeout(CONNECT_TIMEOUT)// 连接超时
            		.setSocketTimeout(SOCKET_TIMEOUT)// 传输超时
            		.build();
            httpPost.setConfig(config);
            // 设置HTTP请求头
            if(headers != null && headers.size() > 0) {
            	for(Entry<String, String> header : headers.entrySet()) {
            		httpPost.setHeader(header.getKey(),header.getValue());
            	}
            }
            // 设置HTTP请求体
            HttpEntity httpRequestEntity = new ByteArrayEntity(content.getBytes(charset));
            httpPost.setEntity(httpRequestEntity);
            // 发送请求
            CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
            // 处理响应
            HttpEntity httpResponseEntity = httpResponse.getEntity();
            result = new String(EntityUtils.toByteArray(httpResponseEntity), charset);
            return result;
        } catch (Exception e) {
            logger.error("处理HTTP~POST异常:" + e.getMessage());
            throw new RuntimeException(e);
        } finally {
        	logger.info("收到的HTTP~POST响应报文:{}", result);
            try {
                httpClient.close();
            } catch (IOException e) {
                logger.error("关闭httpClient异常!", e);
            }
        }
	}
}
