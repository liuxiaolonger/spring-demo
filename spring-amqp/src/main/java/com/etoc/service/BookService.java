package com.etoc.service;

import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.amqp.core.MessageProperties;
import com.alibaba.fastjson.JSONObject;
import com.etoc.bean.Book;
import com.etoc.config.BookRabbitConfig;


/**
 * Created by Admin on 2019/4/20.
 */
@Service
public class BookService implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback {
	public static final Logger logger = LoggerFactory.getLogger(BookService.class);
	@Autowired
	private RabbitTemplate rabbitTemplate;

    /**
     * 发送消息
     *
     * @param orderInfoModel
     */
    public void sendMsg(Book book) {
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setConfirmCallback(this);

        //消息封装
        Message message = MessageBuilder.withBody(JSONObject.toJSONString(book).getBytes()).setContentType(MessageProperties.CONTENT_TYPE_JSON)
                .setContentEncoding("utf-8").setMessageId(book.getBookId()).build();
        CorrelationData correlationData = new CorrelationData(book.getBookId());
        rabbitTemplate.convertAndSend(BookRabbitConfig.EXCHANGE_NAME_TRANSACTION,
        		BookRabbitConfig.ROUTE_NAME_TRANSACTION, message, correlationData);
        logger.info("消息发送》》》》》》");
    }

	/**
	 * 找不到对应的消息
	 * 
	 * @param arg0 消息
	 * @param arg1
	 * @param arg2
	 * @param arg3
	 * @param arg3
	 * @return void
	 * @see [类、类#方法、类#成员]
	 */
	@Override
	public void returnedMessage(Message arg0, int arg1, String arg2, String arg3, String arg4) {
		 logger.info("从新发送消息");
		 rabbitTemplate.send(arg0);
	}

	/**
	 * 确认消息是否发送成功
	 * 
	 * @param arg0 
	 * @param arg1 
	 * @param arg2 
	 * @return void
	 * @see [类、类#方法、类#成员]
	 */
	@Override
	public void confirm(CorrelationData arg0, boolean arg1, String arg2) {
		logger.info(JSONObject.toJSONString(arg0));
		if (arg1) {
			System.out.println("success");
		} else {
			// retry
			System.out.println("failed");
		}
	}
}
