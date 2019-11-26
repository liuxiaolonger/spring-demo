package com.etoc.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class MD5Tool {

	/**
	 * MD5 加密
	 */
	public static String getMD5(String str) {
		MessageDigest messageDigest = null;

		try {
			messageDigest = MessageDigest.getInstance("MD5");

			messageDigest.reset();

			messageDigest.update(str.getBytes("UTF-8"));

		} catch (NoSuchAlgorithmException e) {
			System.out.println("NoSuchAlgorithmException caught!");
			System.exit(-1);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		byte[] byteArray = messageDigest.digest();

		StringBuffer md5StrBuff = new StringBuffer();

		for (int i = 0; i < byteArray.length; i++) {
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
				md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
			else
				md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
		}

		return md5StrBuff.toString();
	}
	
	
	
	public static String getSaltMD5(String pass, String salt, Integer num) {
		MessageDigest messageDigest = null;
		byte[] byteArray = new byte[12];
		try {
			messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.reset();
			if(StringUtil.isNotEmpty(salt)) {
				messageDigest.update(salt.getBytes("UTF-8")); // 先加盐
			}
			byteArray = messageDigest.digest(pass.getBytes("UTF-8")); // 再放需要被加密的数据
			for (int i = 1; i < num; i++) {
				byteArray = messageDigest.digest(byteArray);
			}
		} catch (NoSuchAlgorithmException e) {
			System.out.println("NoSuchAlgorithmException caught!");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		StringBuffer md5StrBuff = new StringBuffer();
		for (int i = 0; i < byteArray.length; i++) {
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
				md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
			else
				md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
		}
		return md5StrBuff.toString();
	}
	
	
	/**
	 * @param length
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public static String getNumRandom(int length) {
		String val = "";
		Random random = new Random();
		//参数length，表示生成几位随机数  
		for (int i = 0; i < length; i++) {
			val += String.valueOf(random.nextInt(10));
		}
		return val;
	}

}
