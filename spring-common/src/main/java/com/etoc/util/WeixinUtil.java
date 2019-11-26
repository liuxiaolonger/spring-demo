/*
 * 文 件 名:  WeixinUtil.java
 * 版    权:  ETOC 信息技术有限公司, Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  ETOC-ChenChao
 * 修改时间:  Jun 20, 2018
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.etoc.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;


/**
 * 微信工具类
 * <功能详细描述>
 * 
 * @author  ETOC-ChenChao
 * @version  [版本号, Jun 20, 2018]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class WeixinUtil {
	private static Logger log = LoggerFactory.getLogger(WeixinUtil.class);
	
	/**
	 * 静默授权，可获取成员的的基础信息
	 */
	public final static String SCOPE_SNSAPI_BASE = "snsapi_base";
	/**
	 * 静默授权，可获取成员的详细信息，但不包含手机、邮箱
	 */
	public final static String SCOPE_SNSAPI_USERINFO = "snsapi_userinfo";
	/**
	 * 手动授权，可获取成员的详细信息，包含手机、邮箱
	 */
	public final static String SCOPE_SNSAPI_PRIVATEINFO = "snsapi_privateinfo";
	
	/**
	 * 企业成员OAuth2.0验证，自动重定向
	 */
	public final static String OAUTH2_AUTHORIZE_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid={corpId}&redirect_uri={redirectUri}&response_type={responseType}&scope={scope}&agentid={agentId}&state={state}#wechat_redirect";
	/**
	 * 获取企业access_token的接口地址
	 */
	public final static String GET_TOKEN_URL = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid={corpId}&corpsecret={corpSecret}";
	/**
	 * 获取用户信息的接口地址
	 */
	public static final String GET_USER_INFO_URL = "https://qyapi.weixin.qq.com/cgi-bin/user/getuserinfo?access_token={accessToken}&code={code}";
	
	/**
	 * 生成oauth2认证的url
	 * <功能详细描述>
	 * @param corpId        企业的CorpID
	 * @param redirectUri   授权后重定向的回调链接地址
	 * @param scope         应用授权作用域。取值snsapi_base：静默授权，snsapi_userinfo：静默授权， snsapi_privateinfo：手动授权
	 * @param agentId       企业应用的id
	 * @param state         重定向后会带上state参数
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public static String oauth2AuthorizeURL(String corpId, String redirectUri, String scope, String agentId, String state) {
		String encodeRedirectUri = null;
		try {
			encodeRedirectUri = URLEncoder.encode(redirectUri, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			encodeRedirectUri = redirectUri;
			log.warn("url地址：{}不支持UTF-8编码", redirectUri);
		}
		return OAUTH2_AUTHORIZE_URL.replaceAll("\\{corpId\\}", corpId)
				.replaceAll("\\{redirectUri\\}", encodeRedirectUri)
				.replaceAll("\\{responseType\\}", "code")// 固定值code
				.replaceAll("\\{scope\\}", scope)
				.replaceAll("\\{agentId\\}", agentId)
				.replaceAll("\\{state\\}", state);
	}
	
	/**
	 * 获取企业access_token
	 * <功能详细描述>
	 * @param corpId        企业ID
	 * @param corpSecret    应用的凭证密钥
	 * @return              返回的结构{"errcode":0,"errmsg":"","access_token": "accesstoken000001","expires_in": 7200}
	 * @see [类、类#方法、类#成员]
	 */
	public static JSONObject getToken(String corpId, String corpSecret) {
		String url = GET_TOKEN_URL
				.replaceAll("\\{corpId\\}", corpId)
				.replaceAll("\\{corpSecret\\}", corpSecret);
		String result = HttpUtil.httpGet(url, new HashMap<String, String>(), "uTF-8");
		return JSONObject.parseObject(result);
	}
	
	/**
	 * 获取企业成员信息
	 * <功能详细描述>
	 * @param accessToken    调用接口凭证
	 * @param code           通过成员授权获取到的code
	 * @return               返回的结构{"errcode": 0,"errmsg": "ok","UserId":"USERID","DeviceId":"DEVICEID","user_ticket": "USER_TICKET","expires_in":7200}
	 * @see [类、类#方法、类#成员]
	 */
	public static JSONObject getUserInfo(String accessToken, String code) {
		String url = GET_USER_INFO_URL
				.replaceAll("\\{accessToken\\}", accessToken)
				.replaceAll("\\{code\\}", code);
		String result = HttpUtil.httpGet(url, new HashMap<String, String>(), "uTF-8");
		return JSONObject.parseObject(result);
	}
}
