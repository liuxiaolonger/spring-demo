/*
 * 文 件 名:  UUIDUtil.java
 * 版    权:  ETOC 信息技术有限公司, Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  ETOC-ChenChao
 * 修改时间:  Jul 30, 2018
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.etoc.util;

import java.util.UUID;

/**
 * UUID工具类
 * 
 * @author  ETOC-ChenChao
 * @version  [版本号, Jul 30, 2018]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class UUIDUtil {
	
	/**
	 * 生成32位长度的UUID字符串
	 * 
	 * @return          UUID字符串
	 * @see java.util.UUID
	 */
    public static String getUUID() {
        UUID uuid = UUID.randomUUID();
        String str = uuid.toString().replace("-", "");
        return str;
    }
    
}
