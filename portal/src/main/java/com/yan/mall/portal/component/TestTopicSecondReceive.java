package com.yan.mall.portal.component;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @description:
 * @author: Ryan
 * @create: 2021-06-18 17:00
 **/
@Component
@RabbitListener(queues = "topic.second")
public class TestTopicSecondReceive {
    @RabbitHandler
    public void process(Map testMessage) {
        System.out.println("TestTopicSecondReceive消费者收到消息  : " + testMessage.toString());
    }
}
