package com.etoc.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/**
 * 订单的消息配置
 * @author liuxiaolong
 *
 */
@Configuration
public class BookRabbitConfig {
    /**
     * 订单派单处理队列
     */
    public static final String QUEUE_NAME_TRANSACTION = "api.book.queue";
    /**
     * 订单补单队列
     */
    public static final String CREATE_QUEUE_NAME_TRANSACTION = "api.book.reCreate.queue";
    /**
     * 订单处理路由KEY
     */
    public static final String ROUTE_NAME_TRANSACTION = "api.book.route";
    /**
     * 订单处理交换机
     */
    public static final String EXCHANGE_NAME_TRANSACTION = "api.book.exchange";

    /**
     * 订单队列
     *
     * @return
     */
    @Bean
    public Queue scoreQueue() {
        return new Queue(QUEUE_NAME_TRANSACTION, true);
    }

    /**
     * 补单队列
     *
     * @return
     */
    @Bean
    public Queue createOrderReceiver() {
        return new Queue(CREATE_QUEUE_NAME_TRANSACTION, true);
    }
    /**
     * 订单处理交换机
     */
    @Bean
    public DirectExchange transExchange() {
        return new DirectExchange(EXCHANGE_NAME_TRANSACTION);
    }

    /**
     * 交换机绑定到订单队列
     * @return
     */
    @Bean
    public Binding bindingExchangeOrderReceiverQueue() {
        return BindingBuilder.bind(scoreQueue()).to(transExchange()).with(ROUTE_NAME_TRANSACTION);
    }

    /**
     * 交换机绑定到补单队列
     * @return
     */
    @Bean
    public Binding bindingExchangeCreateOrderQueue() {
        return BindingBuilder.bind(createOrderReceiver()).to(transExchange()).with(ROUTE_NAME_TRANSACTION);
    }
}
