package com.yan.mall.portal.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description: 主题交换机配置
 *
 * *(星号) 用来表示一个单词 (必须出现的)
 * #(井号) 用来表示任意数量（零个或多个）单词
 * 通配的绑定键是跟队列进行绑定的，举个小例子
 * 队列Q1 绑定键为 *.TT.*   队列Q2绑定键为 TT.#
 * 如果一条消息携带的路由键为 A.TT.B，那么队列Q1将会收到；
 * 如果一条消息携带的路由键为TT.AA.BB，那么队列Q2将会收到；
 *
 * @author: Ryan
 *
 * @create: 2021-06-18 15:47
 **/
@Configuration
public class RabbitMqTopicConfig {

    @Bean
    public Queue firstQueue(){
        return new Queue("topic.first");
    }

    @Bean
    public Queue secondQueue(){
        return new Queue("topic.second");
    }

    @Bean
    TopicExchange exchange(){
        return new TopicExchange("topicExchange");
    }

    /**
     * 将 firstQueue与topicExchange绑定 设置路由键为topic.first
     * @return
     */
    @Bean
    Binding bindingExchangeMessage1(){
        return BindingBuilder
                .bind(firstQueue())
                .to(exchange())
                .with("topic.first");
    }

    /**
     * 将 secondQueue与topicExchange绑定 设置路由键为topic.#(接收通过以topic.开头的所有路由键发送的消息)
     * @return
     */
    @Bean
    Binding bindingExchangeMessage2(){
        return BindingBuilder
                .bind(secondQueue())
                .to(exchange())
                .with("topic.#");
    }
}
