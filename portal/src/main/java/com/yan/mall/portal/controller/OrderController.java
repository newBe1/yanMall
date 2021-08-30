package com.yan.mall.portal.controller;

import com.yan.mall.common.api.CommonResult;
import com.yan.mall.portal.component.CancelOrderSender;
import com.yan.mall.portal.service.OmsPortalOrderService;
import com.yan.mall.portal.service.impl.OmsPortalOrderServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Ryan
 * Date: 2020-11-26
 * Time: 14:55
 */
@Api(tags = "订单管理")
@RequestMapping("/order")
@RestController
public class OrderController {

    @Autowired
    private RabbitTemplate rabbitTemplate;  //使用RabbitTemplate,这提供了接收/发送等等方法

    @Autowired
    private CancelOrderSender cancelOrderSender;

    @ApiOperation("根据购物车信息生成订单")
    @RequestMapping(value = "/generateOrder/{orderId}", method = RequestMethod.GET)
    public CommonResult generateOrder(@PathVariable Long orderId) {


        cancelOrderSender.sendMessage(orderId,10000);
        return CommonResult.success("--下单成功--" + orderId);
    }

    @RequestMapping(value = "/send/direct/test",method = RequestMethod.GET)
    public CommonResult testSend(@RequestParam String meg){
        String messageId = String.valueOf(UUID.randomUUID());
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String,Object> map=new HashMap<>();
        map.put("messageId",messageId);
        map.put("messageData",meg);
        map.put("createTime",createTime);

        //将消息通过绑定路由键 发送至交换机testExchange
        rabbitTemplate.convertAndSend("testExchange","testRouting",map);
        return CommonResult.success(null);
    }

    @GetMapping("/sendTopicMessage1")
    public String sendTopicMessage1() {
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "message: M A N ";
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String, Object> manMap = new HashMap<>();
        manMap.put("messageId", messageId);
        manMap.put("messageData", messageData);
        manMap.put("createTime", createTime);
        rabbitTemplate.convertAndSend("topicExchange", "topic.first", manMap);
        return "ok";
    }

    @GetMapping("/sendTopicMessage2")
    public String sendTopicMessage2() {
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "message: woman is all ";
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String, Object> womanMap = new HashMap<>();
        womanMap.put("messageId", messageId);
        womanMap.put("messageData", messageData);
        womanMap.put("createTime", createTime);
        rabbitTemplate.convertAndSend("topicExchange", "topic.second", womanMap);
        return "ok";
    }
}
