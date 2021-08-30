package com.yan.mall.portal.config;

import com.yan.mall.portal.domain.QueueEnum;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Direct Exchange  直连型交换机配置
 *
 * @description: 消息队列配置  用于配置交换机、队列、队列与交换机的绑定关系
 * 下单 -> 发送延迟取消订单消息至延迟队列 -> 超时 -> 发送取消订单订单消息至取消订单消息队列 -> 执行取消订单操作
 * 1、配置交换机
 * 2、配置存储消息的队列
 * 3、绑定队列（设置路由键）
 *
 * @author: Ryan
 * @create: 2021-06-17 15:44
 **/
@Configuration
public class RabbitMqDirectConfig {

    /**
     * 订单取消消息实际消费队列所绑定的交换机
     * @return
     */
    @Bean
    DirectExchange orderDirect(){
        return (DirectExchange) ExchangeBuilder
                .directExchange(QueueEnum.QUEUE_ORDER_CANCEL.getExchange())
                .durable(true)   //队列是否持久化
                .build();
    }

    /**
     * 订单延迟消息消费队列绑定的交换机
     * @return
     */
    @Bean
    DirectExchange orderTtlDirect(){
        return (DirectExchange) ExchangeBuilder
                .directExchange(QueueEnum.QUEUE_TTL_ORDER_CANCEL.getExchange())
                .durable(true)   //队列是否持久化
                .build();
    }

    /**
     * 订单取消消息实际消费队列
     * @return
     */
    @Bean
    public Queue orderQueue(){
        /**
         * name     队列名称
         * durable  是否持久化 默认true；true(持久化队列)：会被存储在磁盘上，当消息队列重启后消息依然存在；false(暂存队列)：当前连接有效
         * exclusive 是否专用，默认false，只能被当前连接使用，连接关闭后队列立即删除 优先级高于durable
         * autoDelete 是否自动删除，默认false，当没有生产者或者消费者使用此队列，该队列会自动删除
         *
         * return new Queue(QueueEnum.QUEUE_ORDER_CANCEL.getName()，true,false,false);
         */
        return new Queue(QueueEnum.QUEUE_ORDER_CANCEL.getName());
    }

    /**
     * 订单延迟消息队列          消息到期后转发到 订单取消消息队列
     * @return
     */
    @Bean
    public Queue orderTtlQueue(){
        return QueueBuilder
                .durable(QueueEnum.QUEUE_TTL_ORDER_CANCEL.getName())
                .withArgument("x-dead-letter-exchange",QueueEnum.QUEUE_ORDER_CANCEL.getExchange())    //到期后消息转发的交换机
                .withArgument("x-dead-letter-routing-key",QueueEnum.QUEUE_ORDER_CANCEL.getRouteKey()) //到期后消息转发的路由键)
                .build();
    }

    /**
     * 将订单队列绑定到对应交换机
     * @param orderDirect
     * @param orderQueue
     * @return
     */
    @Bean
    Binding orderBinding(DirectExchange orderDirect , Queue orderQueue){
        return BindingBuilder
                .bind(orderQueue)
                .to(orderDirect)
                .with(QueueEnum.QUEUE_ORDER_CANCEL.getRouteKey());
    }

    /**
     * 将订单延迟队列绑定对应交换机
     * @param orderTtlDirect
     * @param orderTtlQueue
     * @return
     */
    @Bean
    Binding orderTtlBinding(DirectExchange orderTtlDirect , Queue orderTtlQueue){
        return BindingBuilder
                .bind(orderTtlQueue)
                .to(orderTtlDirect)
                .with(QueueEnum.QUEUE_TTL_ORDER_CANCEL.getRouteKey());
    }


    /**
     * 测试交换机
     * @return
     */
    @Bean
    DirectExchange directExchange(){
        return new DirectExchange("directExchange",true,false);
    }

    /**
     * 测试队列
     * @return
     */
    @Bean
    Queue directQueue(){
        return new Queue("directQueue");
    }

    /**
     * 测试绑定
     * 注意：绑定的交换机与队列要与 Bean注入的保持相同名称
     * @param direct
     * @param queue
     * @return
     */
    @Bean
    Binding testBinding(@Qualifier("directExchange") DirectExchange direct , @Qualifier("directQueue") Queue queue){
        return BindingBuilder
                .bind(queue)
                .to(direct)
                .with("directRouting");
    }

}
