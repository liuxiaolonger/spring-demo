package com.etoc.util;

import java.io.IOException;
import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * json字符串和java bean对象相互转换工具
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  ETOC-ChenChao
 * @version  [版本号, Aug 20, 2018]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class JsonUtil {
	private static Logger logger = LoggerFactory.getLogger(JsonUtil.class);
	
	/**
	 * json字符串转javabean
	 * <功能详细描述>
	 * @param jsonString    json字符串
	 * @param clazz         java类类型
	 * @return              如果不能转换，则返回null
	 * @see [类、类#方法、类#成员]
	 */
	public static <T> T jsonToBean(String jsonString, Class<T> clazz) {
		T pojo = null;
		try
        {
            ObjectMapper objMapper = new ObjectMapper();
            objMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
            objMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            objMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
            return objMapper.readValue(jsonString, clazz);
        }
        catch (IOException e)
        {
        	logger.warn(jsonString + "无法转换成java bean对象", e);
        }
		return pojo;
	}
	
	/**
	 * json字符串转javabean
	 * <功能详细描述>
	 * @param jsonString    json字符串
	 * @param javaType      JavaType类型及相关子类型
	 * @return              如果不能转换，则返回null
	 * @see [类、类#方法、类#成员]
	 */
	public static <T> T jsonToBean(String jsonString, JavaType javaType) {
		T pojo = null;
		try
        {
            ObjectMapper objMapper = new ObjectMapper();
            objMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
            objMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            objMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
            return objMapper.readValue(jsonString, javaType);
        }
        catch (IOException e)
        {
        	logger.warn(jsonString + "无法转换成java bean对象", e);
        }
		return pojo;
	}
	
	/**
	 * json字符串转javabean
	 * <功能详细描述>
	 * @param jsonString    json字符串
	 * @param typeRef       TypeReference类型，支持嵌套的泛型
	 * @return              如果不能转换，则返回null
	 * @see [类、类#方法、类#成员]
	 */
	public static <T> T jsonToBean(String jsonString, TypeReference<T> typeRef) {
		T pojo = null;
		ObjectMapper objMapper = new ObjectMapper();
		try
        {
            objMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
            // 在转换时，Json字符串中有而Java对象没有的属性，屏蔽报异常
            objMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            objMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
            return objMapper.readValue(jsonString, typeRef);
        } catch (IOException e) {
        	logger.warn(jsonString + "无法转换成java bean对象", e);
        }
		return pojo;
	}

	/**
	 * 将java bean对象转换为json字符串
	 * @param javaBean            java bean对象
	 * @return                    如果不能转换，则返回空字符串
	 */
	public static String beanToJson(Object javaBean)
	{
		return beanToJson(javaBean, false);
	}
	
	/**
	 * 将java bean对象转换为json字符串
	 * @param javaBean            java bean对象
	 * @param nullNotOutPut       是否序列化空对象、字段
	 * @return                    如果不能转换，则返回空字符串
	 */
	public static String beanToJson(Object javaBean, boolean nullNotOutPut)
	{
		String jsonString = "";
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
			if(nullNotOutPut){
				mapper.setSerializationInclusion(Include.NON_NULL);
			}
			jsonString = mapper.writeValueAsString(javaBean);
		} catch (JsonProcessingException e) {
			logger.warn(javaBean + "无法转换成json字符串", e);
		}
		return jsonString;
	}
	
}
