package com.yan.mall.portal.domain;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Created with IntelliJ IDEA.
 * Description:购物车中促销信息的封装
 * User: Ryan
 * Date: 2020-11-26
 * Time: 16:24
 */
@Data
public class CartPromotionItem {
    //促销活动信息
    private String promotionMessage;
    //促销活动减去的金额，针对每个商品
    private BigDecimal reduceAmount;
    //商品的真实库存（剩余库存-锁定库存）
    private Integer realStock;
    //购买商品赠送积分
    private Integer integration;
    //购买商品赠送成长值
    private Integer growth;
}
