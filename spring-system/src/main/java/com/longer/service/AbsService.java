package com.longer.service;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.etoc.util.DateUtil;

/**
 * 
 * 业务层的抽象类
 * 
 *
 * @author 朱鹏
 * @version [版本号, 2019年1月3日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public abstract class AbsService {
	private static final String MODEL_PACKAGE_NAME = "com.longer.dao.model"; // 数据库对象的包
	private static final String METHOD_GET = "get"; // get方法的名称
	private static final String METHOD_SET = "set"; // set方法的名称
	private static final String METHOD_NAME = "name"; // name方法的名称
	private static final String SPLIT_SLASH = "/"; // 切割字符串的符号

	/*
	 * vo和model字段映射的map,key为vo字段,value为model字段
	 */
	private Map<String, String> voFieldToModelField;

	public AbsService() {
		String[] transferFields = transferField();
		voFieldToModelField = new HashMap<>();

		if (transferFields == null)
			return;

		for (String transferFieldstr : transferFields) {
			String[] transferField = transferFieldstr.split(SPLIT_SLASH);
			voFieldToModelField.put(transferField[0], transferField[1]);
		}
	}

	/**
	 * 将model对象和vo对象互相转换 注：参数fieldsStr，传入的字段名称，如果实体类中没有，则会忽略此字段
	 * 
	 * @param request   model对象或vo对象
	 * @param clazz     需要转换成的对象的class
	 * @param fieldsStr 需要返回的字段(的名称)，如果没有，则是返回所有字段
	 * @return
	 * @throws Exception
	 * @see [类、类#方法、类#成员]
	 */
	protected <T> T transferObjectFields(Object request, Class<T> clazz, String[] fieldsStr) throws Exception {
		return transferObjectFields(request, clazz, fieldsStr, null);
	}

	/**
	 * 将model对象和vo对象互相转换 注：1.参数fieldsStr，传入的字段名称，如果实体类中没有，则会忽略此字段
	 * 2.参数objMap是response(返回)对象中，拥有request对象没有的属性时，会使用到的参数;
	 * key为属性名称，value为该属性的值(可以是任意对象)
	 * 
	 * @param request   model对象或vo对象
	 * @param clazz     需要转换成的对象的class
	 * @param fieldsStr 需要返回的字段(的名称)，如果没有，则是返回所有字段
	 * @param objMap    转换成的对象中有额外的属性，放到此参数中进行赋值
	 * @return
	 * @throws Exception
	 * @see [类、类#方法、类#成员]
	 */
	protected <T> T transferObjectFields(Object request, Class<T> clazz, String[] fieldsStr, Map<String, Object> objMap) throws Exception {
		if (request == null)
			return null;
		Class<?> requestClass = Class.forName(request.getClass().getName());
		Class<T> responseClass = clazz;
		// 如果没有需要转换的字段数组,则将全部的vo字段作为需要转换的数组
		if (fieldsStr == null || fieldsStr.length == 0) {
			Field[] fields = null;
			if (classIsModel(requestClass))
				fields = responseClass.getDeclaredFields();
			else
				fields = requestClass.getDeclaredFields();
			fieldsStr = new String[fields.length];
			for (int i = 0; i < fields.length; i++) {
				fieldsStr[i] = fields[i].getName();
			}
		}

		// new一个对象
		Constructor<T> constructorResponse = responseClass.getDeclaredConstructor();
		T response = constructorResponse.newInstance();

		// 根据字段名称,将传入对象的字段值转为返回对象的字段值
		for (String fieldName : fieldsStr) {
			Field requestField = null;
			Field responseField = null;
			try {
				// 获取字段
				responseField = voFieldToModelField(responseClass, fieldName);
				requestField = voFieldToModelField(requestClass, fieldName);
			} catch (NoSuchFieldException e) {
				// 没有找到此字段,则在传入的额外参数(objMap)中找，如果还是没有找到(或传入的参数是null)，则直接忽略,跳过此次处理
				if (objMap == null)
					continue;
				Object obj = objMap.get(fieldName);
				if (obj != null) {
					Method responseSetMethod = responseClass.getDeclaredMethod(fieldToSet(fieldName), responseField.getType());
					responseSetMethod.invoke(response, obj);
				}
				continue;
			}

			// 调用传入对象中该字段的get方法，获取值，调用返回对象中该字段的set方法赋值
			Method responseSetMethod = responseClass.getDeclaredMethod(fieldToSet(responseField.getName()), responseField.getType());
			Method requestGetMethod = requestClass.getDeclaredMethod(fieldToGet(requestField.getName()));
			Object obj = objHandle(requestGetMethod.invoke(request), responseField.getType());
			responseSetMethod.invoke(response, obj);
		}
		return response;
	}

	/**
	 * 验证对象是否是空对象 1.对象是否为null 2.检验对象内的属性是否是空，忽略字段不验证
	 * 
	 * @param obj         需要检验的对象
	 * @param ignoreField 需要忽略的属性名称
	 * @return
	 * @throws Exception
	 * @see [类、类#方法、类#成员]
	 */
	protected boolean objectIsEmpty(Object obj, String... ignoreField) throws Exception {
		if (obj == null)
			return true;
		// 获取需要忽略处理的字段名
		List<String> ignoreFieldList = new ArrayList<>();
		if (ignoreField != null)
			ignoreFieldList = Arrays.asList(ignoreField);

		Class<?> objClass = obj.getClass();
		Field[] fields = objClass.getDeclaredFields();
		// 对每个字段进行验证
		for (Field field : fields) {
			String fieldName = field.getName();
			if (ignoreFieldList.contains(fieldName)) // 跳过忽略字段
				continue;

			Method method = objClass.getDeclaredMethod(fieldToGet(fieldName));
			Object fieldValue = method.invoke(obj);
			if (fieldValue == null) // 跳过为null的字段
				continue;

			if ((fieldValue instanceof String) && String.valueOf(fieldValue).isEmpty()) // 跳过Sting的空字符串
				continue;

			if ((fieldValue instanceof Collection) && ((Collection<?>) fieldValue).isEmpty()) // 跳过空集合
				continue;

			if (fieldValue instanceof Map && ((Map<?, ?>) fieldValue).isEmpty()) // 跳过空map
				continue;

			if (fieldValue.getClass().isArray() && arrayIsEmpty(fieldValue)) // 跳过空数组
				continue;

			// 前面都没跳过,则认为字段有值,返回false
			return false;
		}
		return true;
	}

	/**
	 * 获取映射字段的数组 <功能详细描述>
	 * 
	 * @param name
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	protected abstract String[] transferField();

	/**
	 * 字段名称，转成get方法名称 <功能详细描述>
	 * 
	 * @param field
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	private String fieldToGet(String field) {
		return METHOD_GET + captureName(field);
	}

	/**
	 * 字段名称，转成set方法名称 <功能详细描述>
	 * 
	 * @param field
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	private String fieldToSet(String field) {
		return METHOD_SET + captureName(field);
	}

	/**
	 * 首字母大写 <功能详细描述>
	 * 
	 * @param name
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	private String captureName(String name) {
		char[] cs = name.toCharArray();
		cs[0] -= 32;
		return String.valueOf(cs);
	}

	/**
	 * 获取request的字段 如果requestClass是数据库实体类(model)，则将vo的字段名映射成model的字段名，再获取字段
	 * 如果requestClass是返回实体类(vo),则直接获取字段
	 * 
	 * @param requestClass
	 * @param fieldName
	 * @return
	 * @throws Exception
	 * @see [类、类#方法、类#成员]
	 */
	private Field voFieldToModelField(Class<?> requestClass, String fieldName) throws Exception {
		if (classIsModel(requestClass) && StringUtils.isNotEmpty(voFieldToModelField.get(fieldName)))
			fieldName = voFieldToModelField.get(fieldName);

		// 当找不到字段时，到父类去找，一直找到Objec为止
		while (requestClass != Object.class) {
			try {
				return requestClass.getDeclaredField(fieldName);
			} catch (NoSuchFieldException e) {
				requestClass = requestClass.getSuperclass(); // 得到父类,然后赋给自己
			}
		}
		throw new NoSuchFieldException(fieldName);
	}

	/**
	 * 判断class是否是数据库实体类 数据库实体类包名是com.longer.dao.model
	 * 
	 * @param requestClass
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	private boolean classIsModel(Class<?> requestClass) {
		String packageName = requestClass.getPackage().getName();
		if (MODEL_PACKAGE_NAME.equals(packageName))
			return true;
		return false;
	}

	/**
	 * 判断数组是否为空数组 优先判断8种基本类型的数组，如果不是基本类型数组，则转为Object的数组进行判断
	 * 
	 * @param fieldValue
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	private boolean arrayIsEmpty(Object fieldValue) {
		Class<?> arrayType = fieldValue.getClass().getComponentType();
		if (arrayType == byte.class)
			return ((byte[]) fieldValue).length == 0;
		if (arrayType == short.class)
			return ((short[]) fieldValue).length == 0;
		if (arrayType == int.class)
			return ((int[]) fieldValue).length == 0;
		if (arrayType == long.class)
			return ((long[]) fieldValue).length == 0;
		if (arrayType == float.class)
			return ((float[]) fieldValue).length == 0;
		if (arrayType == double.class)
			return ((double[]) fieldValue).length == 0;
		if (arrayType == char.class)
			return ((char[]) fieldValue).length == 0;
		if (arrayType == boolean.class)
			return ((boolean[]) fieldValue).length == 0;
		return ((Object[]) fieldValue).length == 0;
	}

	/**
	 * 处理特殊属性值的类型转换问题 将get方法获取到的值的类型，转为set方法参数的类型 1.String转Date 2.Date转String
	 * 3.enum转String
	 * 
	 * @param obj               get方法获取的值
	 * @param responseFieldType set方法参数类型
	 * @return
	 * @throws Exception
	 * @see [类、类#方法、类#成员]
	 */
	private Object objHandle(Object obj, Class<?> responseFieldType) throws Exception {
		if (obj == null)
			return obj;

		if (responseFieldType == Date.class && obj instanceof String)
			obj = DateUtil.convertString2Date((String) obj, DateUtil.DATE_SPLIT_FORMAT);

		if (responseFieldType == String.class && obj instanceof Date)
			obj = DateUtil.convertDate2String((Date) obj);

		if (responseFieldType == String.class && obj.getClass().isEnum()) {
			Method m = obj.getClass().getMethod(METHOD_NAME);
			obj = m.invoke(obj);
		}
		return obj;
	}

}
