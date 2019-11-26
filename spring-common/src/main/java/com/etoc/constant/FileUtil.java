package com.etoc.constant;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.etoc.exception.ChannelException;
import com.etoc.util.StringUtil;
import com.etoc.util.UUIDUtil;

public class FileUtil {

	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(PhoneUtil.class);

	/**
	 * utf-8编码的txt，截取掉第一个字符后，读取文件 <功能详细描述>
	 * 
	 * @param filePath
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public static List<String> analyticalTXT(String filePath) {
		List<String> fileList = new ArrayList<String>();

		FileInputStream inputStream = null;
		InputStreamReader streamReader = null;
		BufferedReader reader = null;
		try {
			// request.setFilePath("c:/Java/mobile.txt");
			if (!new File(filePath).exists()) {
				return fileList;
			}

			inputStream = new FileInputStream(filePath);
			streamReader = new InputStreamReader(inputStream);
			reader = new BufferedReader(streamReader);
			// 截取掉第一个字符
			reader.skip(1);
			String line = "";
			while ((line = reader.readLine()) != null) {
				// 如果号码不为空，并且长度是手机号类型
				if (StringUtils.isNoneEmpty(line)) {
					String phone = line.trim();
					fileList.add(phone);
				}
			}
			logger.info("ReadFileHandle readFile success");
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				if (reader != null)
					reader.close();
			} catch (IOException e) {
			}
			try {
				if (streamReader != null)
					streamReader.close();
			} catch (IOException e) {
			}
			try {
				if (inputStream != null)
					inputStream.close();
			} catch (IOException e) {
			}
		}

		return fileList;
	}

	/**
	 * 单个文件上传 上传附件
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String uploadFile(MultipartFile fileNames, String path) throws Exception {
		String fileUrl = null;
		try {
			// 文件类型:file.getContentType()
			// 文件名称:file.getName()
			if (StringUtil.isNotEmpty(fileNames.getOriginalFilename())) {
				if (fileNames.getSize() > Integer.MAX_VALUE) {// 文件长度
					throw new ChannelException("上传文件过大!");
				}
				InputStream is = fileNames.getInputStream();// 多文件也适用
				File pFile = new File(path);
				// exists()测试此抽象路径名表示的文件或目录是否存在 isDirectory()检查一个对象是否是文件夹,如果是则返回true，否则返回false
				if (!pFile.exists() && !pFile.isDirectory()) {
					// mkdir()只会建立一级的文件夹
					pFile.mkdir();
				}
				// 截取字符串用来拼接文件名字
				String[] str = fileNames.getOriginalFilename().split("\\.");
				String type = str[str.length - 1];
				String newFileName = UUIDUtil.getUUID() + "." + type;
				File f = new File(pFile, newFileName);
				// f 文件上传路径 is上传文件内容
				FileUtils.copyInputStreamToFile(is, f);
				fileUrl = newFileName;
			}
		} catch (Exception e) {
			throw new ChannelException("上传文件异常!", e);
		}
		return fileUrl;
	}

	// 判断txt是否为utf-8编码
	public boolean isUtf8(String filePath) {
		try {
			BufferedInputStream bin = new BufferedInputStream(new FileInputStream(new File(filePath)));
			byte[] b = new byte[10];

			bin.read(b, 0, b.length);

			String first = toHex(b);
			// 这里可以看到各种编码的前几个字符是什么，gbk编码前面没有多余的
			String code = null;
			if (first.startsWith("EFBBBF")) {
				code = "UTF-8";
				return true;
			} else if (first.startsWith("FEFF00")) {
				code = "UTF-16BE";
			} else if (first.startsWith("FFFE")) {
				code = "Unicode";
			} else if (first.startsWith("FFFE")) {
				code = "Unicode";
			} else {
				code = "GBK";
			}
			System.out.println("上传的txt文件不是utf-8编码！编码code=" + code);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public String toHex(byte[] byteArray) {
		int i;
		StringBuffer buf = new StringBuffer("");
		int len = byteArray.length;
		for (int offset = 0; offset < len; offset++) {
			i = byteArray[offset];
			if (i < 0)
				i += 256;
			if (i < 16)
				buf.append("0");
			buf.append(Integer.toHexString(i));
		}
		return buf.toString().toUpperCase();
	}

}
