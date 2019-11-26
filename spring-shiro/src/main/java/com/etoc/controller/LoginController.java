package com.etoc.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.subject.Subject;
import org.eclipse.jetty.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
//import com.google.common.collect.ImmutableMap;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.etoc.bean.LoginUser;
import com.etoc.exception.ChannelException;
import com.etoc.model.SysUrlFilter;
import com.etoc.model.SysUser;
import com.etoc.service.SysUrlFilterService;
import com.etoc.service.SysUserService;
import com.etoc.utils.TokenUtil;
import com.google.code.kaptcha.impl.DefaultKaptcha;

import net.minidev.json.JSONObject;

/**
 * 
 * 登陆和权限管理 <功能详细描述>
 * 
 * @author chuyh
 * @version [版本号, 2019年3月9日]
 * @see [@CrossOrigin]
 * @since [产品/模块版本]
 */
@RestController
//@CrossOrigin
public class LoginController {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private TokenUtil tokenUtil;
	@Autowired
	private DefaultKaptcha captchaProducer;
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	@Autowired
	private SysUserService userService;
	@Autowired
	private SysUrlFilterService urlService;

	@GetMapping(value = "/captcha/")
	// @ResponseStatus(HttpStatus.OK)
	public ResponseEntity<?> captcha() {
		try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			String capText = captchaProducer.createText();
			String uuid = UUID.randomUUID().toString();
			// 设置redis键值 且超时时间为60秒
			redisTemplate.boundValueOps(uuid).set(capText, 60, TimeUnit.SECONDS);
			BufferedImage bi = captchaProducer.createImage(capText);
			ImageIO.write(bi, "png", baos);
			String imgBase64 = Base64.encodeToString(baos.toByteArray()); // encodeBase64String()
			Map<String, String> map = new HashMap<>();
			map.put("uuid", uuid);
			map.put(uuid, "data:image/jpeg;base64," + imgBase64);
			return new ResponseEntity<Object>(map, HttpStatus.OK);
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	@PostMapping("/login/")
	// @ResponseStatus(HttpStatus.OK)
	public ResponseEntity<?> login(@RequestBody LoginUser loginUser, HttpServletRequest request,
			HttpServletResponse response) throws IOException, Exception {
		JSONObject jsonObject = new JSONObject();
		// 判断验证码
		if (StringUtil.isBlank(loginUser.getVcode()))
			throw new ChannelException("验证码不能为空!!!");
		if (StringUtil.isBlank(loginUser.getVcodeKey()))
			throw new ChannelException("验证码键不能为空!!!");
		// 从redis中获取验证码
		String result = (String) redisTemplate.boundValueOps(loginUser.getVcodeKey()).get();

		if (StringUtil.isBlank(result)) {
			jsonObject.put("code", com.etoc.utils.HttpStatus.IS_TIMEOUT);
			jsonObject.put("msg", "验证码过期!");
			jsonObject.put("timestamp", Calendar.getInstance().getTimeInMillis());
			// return jsonObject;
			return new ResponseEntity<Object>(jsonObject, HttpStatus.FORBIDDEN);
		}
		if (!loginUser.getVcode().equals(result)) {
			jsonObject.put("code", com.etoc.utils.HttpStatus.CODE_MISMATHC);
			jsonObject.put("msg", "输入的验证码错误");
			jsonObject.put("timestamp", Calendar.getInstance().getTimeInMillis());
			// return jsonObject;
			return new ResponseEntity<Object>(jsonObject, HttpStatus.FORBIDDEN);
		}
		// 判断用户名和密码是否正确
//		String loginName = loginUser.getUsername();
//        JWTToken jwtToken=new JWTToken(tokenUtil.generateToken(loginName));
		//判断用户名和密码是否正确
        String loginName = loginUser.getUsername();
        // MD5加密登录密码
        String password = loginUser.getPassword();    
        UsernamePasswordToken userPasswordToken = new UsernamePasswordToken(loginName, password);
        Subject subject = SecurityUtils.getSubject();
		try {
			// 该方法会调用JwtRealm中的doGetAuthenticationInfo方法
			subject.login(userPasswordToken);
		} catch (UnknownAccountException exception) {
			exception.printStackTrace();
			System.out.println("账号不存在");
			jsonObject.put("code", com.etoc.utils.HttpStatus.CODE_MISMATHC);
			jsonObject.put("msg", "输入的验证码错误");
			jsonObject.put("timestamp", Calendar.getInstance().getTimeInMillis());
			// return jsonObject;
			return new ResponseEntity<Object>(jsonObject, HttpStatus.FORBIDDEN);
		} catch (IncorrectCredentialsException exception) {
			exception.printStackTrace();
			System.out.println("错误的凭证，用户名或密码不正确");
			jsonObject.put("code", com.etoc.utils.HttpStatus.CODE_MISMATHC);
			jsonObject.put("msg", "输入的验证码错误");
			jsonObject.put("timestamp", Calendar.getInstance().getTimeInMillis());
			// return jsonObject;
			return new ResponseEntity<Object>(jsonObject, HttpStatus.FORBIDDEN);
		} catch (LockedAccountException exception) {
			exception.printStackTrace();
			System.out.println("账户已锁定");
		} catch (ExcessiveAttemptsException exception) {
			exception.printStackTrace();
			System.out.println("错误次数过多");
		} catch (AuthenticationException exception) {
			exception.printStackTrace();
			System.out.println("认证失败");
		}

		// 认证通过
		if (subject.isAuthenticated()) {
			// 验证用户名密码成功后生成token
			String token = tokenUtil.generateToken(loginName);
            Cookie cookie = new Cookie("token", token);
            cookie.setHttpOnly(true);
            cookie.setMaxAge(3600 * 5);
            cookie.setPath("/");
            response.addCookie(cookie);
			// 根据用户名查询数据库得到用户对象
			SysUser user=  userService.getByLoginName(loginName);
			user.setLoginPsw(null);// 清除密码信息 (安全信息)
			user.setSalt(null);// 清除盐信息 (安全需要)
			jsonObject.put("code", HttpStatus.OK);
			jsonObject.put("msg", "success");
			jsonObject.put("token", token);
			jsonObject.put("user", user);
			jsonObject.put("timestamp", Calendar.getInstance().getTimeInMillis());
			// return jsonObject;
			return new ResponseEntity<Object>(jsonObject, HttpStatus.OK);
		} else {
			jsonObject.put("code", HttpStatus.FORBIDDEN);
			jsonObject.put("msg", "error");
			jsonObject.put("timestamp", Calendar.getInstance().getTimeInMillis());
			// return jsonObject;
			return new ResponseEntity<Object>(jsonObject, HttpStatus.FORBIDDEN);
		}
	}

    /**
     * 检查是否有访问权限
     * 
     * @param token
     * @return
     */
    @GetMapping(value = "/permitted/")
    public Object checkLogin(String token, String className, String method, String httpUri, String httpMethod)
    {
        logger.info("权限认证,className={},method={},httpUri={},httpMethod={}",className,method,httpUri,httpMethod);
        // 根据请求方法、路径，获取请求所需要的权限
        Subject subject = SecurityUtils.getSubject();
        
        JSONObject jsonObject = new JSONObject();
        // 获取当前按钮所需要的权限字符串permission
        SysUrlFilter urlFilter = urlService.selectByUrl(httpUri, httpMethod);
        if (urlFilter == null)
        {
            logger.error("SysUrlFilter is null!!!");
            // 认证不通过
            jsonObject.put("code", HttpStatus.FORBIDDEN);
            jsonObject.put("msg", "error");
            jsonObject.put("timestamp", Calendar.getInstance().getTimeInMillis());
            return jsonObject;
        }
        // 判断该用户有没有这个按钮的角色或者权限
        boolean verificationResults =
            subject.hasRole(urlFilter.getRoles()) || subject.isPermitted(urlFilter.getPermissions());
        //System.out.println("用户权限判断结果：" + verificationResults);
        
        if (verificationResults)
        {
            // 认证通过
            jsonObject.put("code", HttpStatus.OK);
            jsonObject.put("msg", "success");
            jsonObject.put("token", token);
            jsonObject.put("timestamp", Calendar.getInstance().getTimeInMillis());
        }
        else
        {
            logger.info("权限认证失败!!!");
            // 认证不通过
            jsonObject.put("code", HttpStatus.FORBIDDEN);
            jsonObject.put("msg", "error");
            jsonObject.put("timestamp", Calendar.getInstance().getTimeInMillis());
        }
        if (StringUtils.isEmpty(token))
        {
            jsonObject.put("msg", "令牌为空");
        }
        logger.info("权限认证成功!!!");
        return jsonObject;
    }
    
    /**
     * 登出
     * 
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @GetMapping(value = "/logout")
    @ResponseStatus(HttpStatus.OK)
    public Object logout(HttpServletRequest request, HttpServletResponse response)
        throws IOException
    {
        Optional<Cookie> cookie =
            Arrays.stream(request.getCookies()).filter(ck -> "token".equals(ck.getName())).limit(1).map(ck -> {
                ck.setMaxAge(0);
                ck.setHttpOnly(true);
                ck.setPath("/");
                return ck;
            }).findFirst();
        response.addCookie(cookie.get());
        response.flushBuffer();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", 200);
        jsonObject.put("msg", "success");
        return jsonObject;
    }
    
    /**
     * 更新token
     * 
     * @param token
     * @return
     */
    @PostMapping("/token/refresh")
    public Object refreshToken(@CookieValue(value = "token") String token)
    {
        JSONObject jsonObject = new JSONObject();
        String newToken = tokenUtil.refreshToken(token);
        jsonObject.put("code", 200);
        jsonObject.put("msg", "success");
        jsonObject.put("token", newToken);
        jsonObject.put("timestamp", Calendar.getInstance().getTimeInMillis());
        return jsonObject;
    }
}
