package com.etoc.constant;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;

import sun.misc.BASE64Decoder;

public class PhoneUtil {
    
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(PhoneUtil.class);
	
	//TODO 需完善: 暂时只接受联通号码为合法号码
	private static final String[] VALID_UNI_PHONE_PREFIX = new String[] { 
	    "130", "131", "132", "145","146", "155", "156","166","1704", "1707","1708","1709","171","175", "176", "186", "185" ,
	    "149","1700","1701","1702","180","181","189","133","134","153","177","173",
	    "139","138","137","136","135","134","140","147","1478","1571","159","150","151","152","158","157","1650","1651","1652","1703","1705","1706","1720","1721","1722","1723","1724","1728","1729","178","1785",
		"182","1821","1832","1840","1841","1842","1843","1844","1845","1846","1847","1848","1870","1878","1884","1888","187","188","183","184","198","1980","1981","1982","1983","1984","1985","1986","1987","1988","1989"};

	private final static String[] CU = new String[] { "130", "131", "132", "145","146","155","156","166","1704", "1707","1708","1709","171","175", "176", "186", "185"};
	private final static String[] CN = new String[] { "149","1700","1701","1702","180","181","189","133","134","153","177","173"};
	private final static String[] CM = new String[] { "139","138","137","136","135","134","140","147","1478","1571","159","150","151","152","158","157","1650","1651","1652","1703","1705","1706","1720","1721","1722","1723","1724","1728","1729","178","1785",
		"182","1821","1832","1840","1841","1842","1843","1844","1845","1846","1847","1848","1870","1878","1884","1888","187","188","183","184","198","1980","1981","1982","1983","1984","1985","1986","1987","1988","1989"};
    
	/**
	 * return the phone number or "0"
	 * */
	public static String parsePhoneNum(String phoneInRequest) {
		String phoneNum = decodeBase64(phoneInRequest);
		// if the decode result not right, use the original phone num
		if (phoneNum != null && phoneNum.length() != 11) {
			phoneNum = phoneInRequest;
		}
		// if phone num's format is incorrect, set it as "0"
		if (phoneNum == null || "".equals(phoneNum) || phoneNum.length() != 11) {
			phoneNum = "0";
		}
		return phoneNum;
	}
	
	public static String decodeBase64(String s) {
		String rstString = null;
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			byte[] b = decoder.decodeBuffer(s);
			rstString = new String(b);
		} catch (Exception e) {
			rstString = s;
		}
		return rstString;
	}
	
	public static boolean validPhoneNum(String phone) {
		if (phone == null || phone.length() != 11) {
			return false;
		}
		for (String x : VALID_UNI_PHONE_PREFIX) {
			if (phone.substring(0, x.length()).equals(x)) {
				return true;
			}
		}
		return false;
	}

	public static boolean validPhoneNum(String phone,String mobileOperator) {
		if (phone == null || phone.length() != 11) {
			return false;
		}
		if("CU".equals(mobileOperator)){
			for (String x : CU) {
				if (phone.substring(0, x.length()).equals(x)) {
					return true;
				}
			}
		}else if("CN".equals(mobileOperator)){
			for (String x : CN) {
				if (phone.substring(0, x.length()).equals(x)) {
					return true;
				}
			}
		}else if("CM".equals(mobileOperator)){
			for (String x : CM) {
				if (phone.substring(0, x.length()).equals(x)) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	

	/**
	 * 国际化手机号，截取后11位
	 * <功能详细描述>
	 * @param phone
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public static String subStringPhone(String phone)
	{
	    if (phone != null && phone.length() == 13) {
            return phone.substring(2);
        }
	    return phone;	    
	}
	
	/**
	 * <一句话功能简述>
	 * <功能详细描述>
	 * @param filePath
	 * @param mobileOperator
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public static List<String> analyticalTXT(String filePath)
	{
	    List<String> phoneList = new ArrayList<String>();
        
        FileInputStream inputStream = null;
        InputStreamReader streamReader = null;
        BufferedReader reader = null;
        try
        {   
            //request.setFilePath("c:/Java/mobile.txt");
            if(!new File(filePath).exists()){
                return phoneList;
            }
                
            inputStream = new FileInputStream(filePath);
            streamReader = new InputStreamReader(inputStream);
            reader = new BufferedReader(streamReader);
            
            String line = "";
            while((line = reader.readLine()) != null)
            {                
                // 如果号码不为空，并且长度是手机号类型
                if (StringUtils.isNoneEmpty(line))
                {
                    String phone = line.trim();
                    phoneList.add(phone);
                }
            }
            logger.info("ReadFileHandle readFile success");
        }
        catch (Exception e)
        {
            logger.error(e.getMessage());
            e.printStackTrace();
        } 
        finally
        {
            try
            { if (reader != null)reader.close(); }
            catch (IOException e){}
            try
            { if (streamReader != null) streamReader.close(); }
            catch (IOException e) {}
            try
            { if (inputStream != null) inputStream.close(); }
            catch (IOException e) {}
        }  

        return phoneList;
	}

	public static void main(String[] args){
		System.out.println(validPhoneNum("18391478242"));
	}
}
