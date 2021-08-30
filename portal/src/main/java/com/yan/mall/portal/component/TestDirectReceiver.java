package com.yan.mall.portal.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @description: 测试消费者
 * @author: Ryan
 * @create: 2021-06-18 14:29
 **/
@Component
@Slf4j
@RabbitListener(queues = {"directQueue","mall.order.cancel"})   //监听的队列名称
public class TestDirectReceiver {

    @RabbitHandler
    public void handle(Map testMsg){
        log.info("测试消费者TestReceiver接收到消息 ：" + testMsg.toString());
    }

}
