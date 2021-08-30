package com.yan.mall.portal.component;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @description: 测试topic交换机消息接收者
 * @author: Ryan
 * @create: 2021-06-18 16:58
 **/
@Component
@RabbitListener(queues = "topic.first")
public class TestTopicFirstReceive {

    @RabbitHandler
    public void process(Map testMessage) {
        System.out.println("TestTopicFirstReceive消费者收到消息  : " + testMessage.toString());
    }
}
