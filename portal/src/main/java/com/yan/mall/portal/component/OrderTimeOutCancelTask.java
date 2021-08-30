package com.yan.mall.portal.component;

import com.yan.mall.portal.service.OmsPortalOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 * Description: 订单超时未支付 取消订单并解除库存
 * User: Ryan
 * Date: 2020-11-26
 * Time: 14:57
 */
@Component
public class OrderTimeOutCancelTask {
    private Logger LOGGER = LoggerFactory.getLogger(OrderTimeOutCancelTask.class);

    @Resource
    private OmsPortalOrderService portalOrderService;

    /**
     *每10S执行一次
     */
    //@Scheduled(cron = "*/10 * * * * ?")
    private void cancelTimeOutOrder(){
        //TODO 执行取消订单 解除库存的实现代码
        //portalOrderService.cancelTimeOutOrder();
        LOGGER.info("取消订单，并根据sku编号释放锁定库存，取消订单数量：{}");
    }

}
