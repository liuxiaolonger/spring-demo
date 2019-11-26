package com.etoc;

import java.io.File;
import java.util.Random;

import javax.mail.internet.MimeMessage;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.junit4.SpringRunner;

import com.etoc.config.SendMessageUtil;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Springboot04TaskApplicationTests {
	@Autowired
	JavaMailSenderImpl mailSender;


	@Test
	public void contextLoads() {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("896846152@qq.com");
		message.setTo("1643725088@qq.com"); //自己给自己发送邮件
		message.setSubject("主题：简单邮件");
		message.setText("发错了你妹的");
		mailSender.send(message);
	}

	@Test
	public void test()throws  Exception {
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true);
        helper.setSubject("通知.今晚开会");
        helper.setText("<h1 style='color:red'>7:30没来就不用来了</h1>",true);
		helper.setFrom("896846152@qq.com");
		helper.setTo("18872719202@163.com");
		helper.addAttachment("1.docker笔记.txt",new File("C:\\Users\\Admin\\Desktop\\新建文件夹\\docker笔记.txt"));
		helper.addAttachment("2.git常用命令.txt",new File("C:\\Users\\Admin\\Desktop\\新建文件夹\\git常用命令.txt"));
		mailSender.send(mimeMessage);
	}
	
	/**
     * 随机生成6位验证码
     * @return
     */
    public static String getRandomCode(){
        Random random = new Random();
        String result="";
        for (int i=0;i<6;i++){
            result+=random.nextInt(10);
        }
        return result;
    }

/**
     * @author：lvfang
     * @mathName： testSendMessage
     * @parameter： 无
     * @return value：
     * @throws null
     * @date 2018/8/11
     * @desc SMS短信测试
     */
    @Test
    public void testSendMessage(){
    	System.out.println("hhahahahahhhahha"+getRandomCode());
//        SendMessageUtil.send("SMS账户","接口秘钥","目标号码","发送内容");           
       Integer count= SendMessageUtil.send("longerr","d41d8cd98f00b204e980","18872719202","【验证码：】"+getRandomCode());
        System.out.println("最终的结果"+count);
    }

}
