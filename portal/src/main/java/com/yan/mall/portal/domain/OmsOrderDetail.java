package com.yan.mall.portal.domain;

import com.yan.mall.model.OmsOrder;
import com.yan.mall.model.OmsOrderItem;
import lombok.Data;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:  订单详情（保护订单中的商品）
 * User: Ryan
 * Date: 2020-11-26
 * Time: 16:16
 */
public class OmsOrderDetail extends OmsOrder {
    private List<OmsOrderItem> orderItemList;

    public List<OmsOrderItem> getOrderItemList() {
        return orderItemList;
    }

    public void setOrderItemList(List<OmsOrderItem> orderItemList) {
        this.orderItemList = orderItemList;
    }

}
