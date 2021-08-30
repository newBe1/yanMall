package com.yan.mall.portal.component;

import com.yan.mall.portal.domain.QueueEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @description: 延迟消息发送者
 * @author: Ryan
 * @create: 2021-06-17 17:15
 **/
@Component
@Slf4j
public class CancelOrderSender {

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void sendMessage(Long orderId , final long delayTimes){
        //给延迟队列 发送消息      交换机  路由键 消息对象
        amqpTemplate.convertAndSend(QueueEnum.QUEUE_TTL_ORDER_CANCEL.getExchange(), QueueEnum.QUEUE_TTL_ORDER_CANCEL.getRouteKey(), orderId, new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {

                //设置 超时时间 ms
                message.getMessageProperties().setExpiration(String.valueOf(delayTimes));

                return message;
            }
        });


        log.info("----send delay message orderId:{}",orderId);
    }
}
