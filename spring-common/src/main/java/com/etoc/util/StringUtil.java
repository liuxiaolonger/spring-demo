package com.etoc.util;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

public class StringUtil
{
	public static boolean isBlank(String txt)
	{
		return StringUtils.isBlank(txt);
	}
	
	public static boolean isNotBlank(String txt)
	{
		return !isBlank(txt);
	}
	
	public static boolean isEmpty(String txt)
	{
		return StringUtils.isEmpty(txt);
	}
	
	public static boolean isNotEmpty(String txt)
	{
		return !isEmpty(txt);
	}
	
	
    /**
     * 生成唯一标示
     * @return
     */
    public static String getUUID()
    {
        UUID uuid = UUID.randomUUID();
        String str = uuid.toString().replace("-", "");
        return str;
    }
    
    //验证model值[0,1,2]
    public static boolean checkModel(String model){
    	boolean check = true;
    	if(null == model){
    		return false;
    	}
    	List<String> modelList = new ArrayList<String>();
    	modelList.add("0");
    	modelList.add("1");
    	modelList.add("2");
    	check = modelList.contains(model);
    	return check;
    	
    }
    
    /**
     * 将数组中单词按单词首字母排序
     * @return
     */
    public static String[] getInitalLetterSort(String[] oldArray){
		Arrays.sort(oldArray,String.CASE_INSENSITIVE_ORDER); 
		return oldArray;
	}
    
    /**
     * 将String字符串转化为Double类型
     * @return
     */
    public static Double StringTurnPercentRangeDouble(String str){
    	Double num = 100D;
    	try{
    		num = Double.valueOf(str) * 100;
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return num;
    }
    
    /**
     * 将String[]转化为String字符串
     * @return
     */
    public static String arrayTurnPercentRangeString(String[] strs,String key){
    	String signature = "";
		StringBuffer signString = new StringBuffer();
    	if(strs == null){
    		return signature;
    	}
    	try{
    		for(String str : strs){
    			signString.append(str);
    		}
    		signString.append(key);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return signString.toString();
    }
    
    /**
     * 随机获取4位验证码
     * @return
     */
	public static int getCode() {
		/*Random ran=new Random();
		int r=0;
		m1:while(true){
		    int n=ran.nextInt(10000);
		    r=n;
		    int[] bs=new int[4];
		    for(int i=0;i<bs.length;i++){
		        bs[i]=n%10;
		        n/=10;
		    }
		    Arrays.sort(bs);
		    for(int i=1;i<bs.length;i++){
		        if(bs[i-1]==bs[i]){
		            continue m1;
		        }
		    }
		    break;
		}
		return r;*/
		int count=new Random().nextInt(9999);
		if(count < 1000){
			count = count + 1000;
		}
		return count;
	}
    
	/**
	 * 将String字符串转化为Double类型
	 * @param str
	 * @return
	 */
	public static double StringTurnDouble(String str){
		double d = 1;
		try {
			d = Double.parseDouble(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return d;
	}
	
    /** 
     * 转换为下划线 
     *  
     * @param camelCaseName 
     * @return 
     */  
    public static String underscoreName(String camelCaseName) { 
        StringBuilder result = new StringBuilder();  
        if (camelCaseName != null && camelCaseName.length() > 0) {  
            result.append(camelCaseName.substring(0, 1).toLowerCase());  
            for (int i = 1; i < camelCaseName.length(); i++) {  
                char ch = camelCaseName.charAt(i);  
                if (Character.isUpperCase(ch)) {  
                    result.append("_");  
                    result.append(Character.toLowerCase(ch));  
                } else {  
                    result.append(ch);  
                }  
            }  
        }  
        return result.toString();  
    }
    
    /** 
     * 转换为驼峰 
     *  
     * @param underscoreName 
     * @return 
     */  
    public static String camelCaseName(String underscoreName) {  
        StringBuilder result = new StringBuilder();  
        if (underscoreName != null && underscoreName.length() > 0) {  
            boolean flag = false;  
            for (int i = 0; i < underscoreName.length(); i++) {  
                char ch = underscoreName.charAt(i);  
                if ("_".charAt(0) == ch) {  
                    flag = true;  
                } else {  
                    if (flag) {  
                        result.append(Character.toUpperCase(ch));  
                        flag = false;  
                    } else {  
                        result.append(ch);  
                    }  
                }  
            }  
        }  
        return result.toString();  
    }
    
    /** 
     * 转换为驼峰 
     *  
     * @param underscoreName 
     * @return 
     */  
    public static String camelUpperCaseName(String underscoreName) {  
        StringBuilder result = new StringBuilder();  
        if (underscoreName != null && underscoreName.length() > 0) {  
            boolean flag = false;  
            underscoreName = underscoreName.toLowerCase();
            for (int i = 0; i < underscoreName.length(); i++) {  
                char ch = underscoreName.charAt(i);  
                if ("_".charAt(0) == ch) {  
                    flag = true;  
                } else {  
                    if (flag) {  
                        result.append(Character.toUpperCase(ch));  
                        flag = false;  
                    } else {  
                        result.append(ch);  
                    }  
                }  
            }  
        }  
        return result.toString();  
    } 
    
    /** 
     * 判断对象或对象数组中每一个对象是否为空: 对象为null，字符序列长度为0，集合类、Map为empty 
     *  
     * @param obj 
     * @return 
     */  
    public static boolean isNullOrEmpty(Object obj) {
        if (obj == null || obj == "null" || obj == "")  
            return true;  
  
        if (obj instanceof CharSequence)  
            return ((CharSequence) obj).length() == 0;  
  
        if (obj instanceof Collection)  
            return ((Collection) obj).isEmpty();  
  
        if (obj instanceof Map)  
            return ((Map) obj).isEmpty();  
        
        if (obj instanceof Object[]) {
            Object[] object = (Object[]) obj;  
            if(obj.getClass().isArray()) {
          	  return Array.getLength(obj) == 0;
          	}  
            boolean empty = true;  
            for (int i = 0; i < object.length; i++) {  
                if (!isNullOrEmpty(object[i])) {  
                    empty = false;  
                    break;  
                }  
            }  
            return empty;  
        }  
        return false;  
    } 
	
	public static void main(String[] args) {
		int random = (int)(Math.random()*100+1);
		
		double areaPercent = 1;
		areaPercent = StringUtil.StringTurnDouble("1");
		
		Double successRatio = StringUtil.StringTurnPercentRangeDouble("0.5");
		successRatio = successRatio*areaPercent;
	//	System.out.println(successRatio);
		
		if(Double.valueOf(random) > successRatio){
			System.out.println("大于");
		}else{
			System.out.println("小于");
		}
		
		//System.out.println(underscoreName("violationEightProvisionsFlag"));
		//System.out.println(camelUpperCaseName("UNIT_OR_EVENT_FlAG"));
		System.out.println(isNullOrEmpty(null));
	}
}
