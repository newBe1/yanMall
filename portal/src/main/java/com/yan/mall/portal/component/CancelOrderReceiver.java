package com.yan.mall.portal.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @description: 取消订单接收者
 * @author: Ryan
 * @create: 2021-06-17 17:22
 **/
@Component
@Slf4j
@RabbitListener(queues = "mall.order.cancel")
public class CancelOrderReceiver {

    @RabbitHandler
    public void handle(Long orderId){
        log.info("----receive delay message orderId:{}",orderId);
    }
}
