package com.yan.mall.portal.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.yan.mall.common.api.CommonPage;
import com.yan.mall.mapper.OmsOrderSettingMapper;
import com.yan.mall.model.OmsOrderSetting;
import com.yan.mall.portal.dao.PortalOrderDao;
import com.yan.mall.portal.domain.ConfirmOrderResult;
import com.yan.mall.portal.domain.OmsOrderDetail;
import com.yan.mall.portal.domain.OrderParam;
import com.yan.mall.portal.service.OmsPortalOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Ryan
 * Date: 2020-11-26
 * Time: 16:28
 */
@Service
public class OmsPortalOrderServiceImpl implements OmsPortalOrderService {
    @Resource
    private OmsOrderSettingMapper orderSettingMapper;

    @Autowired
    private PortalOrderDao portalOrderDao;


    @Override
    public ConfirmOrderResult generateConfirmOrder(List<Long> cartIds) {
        return null;
    }

    @Override
    public Map<String, Object> generateOrder(OrderParam orderParam) {
        return null;
    }

    @Override
    public Integer paySuccess(Long orderId, Integer payType) {
        return null;
    }

    @Override
    public Integer cancelTimeOutOrder() {
        Integer count = 0;

        //获取订单超时设置信息
        OmsOrderSetting orderSetting = orderSettingMapper.selectByPrimaryKey(1L);

        //查询超时的订单
        List<OmsOrderDetail> orderDetails = portalOrderDao.getTimeOutOrders(orderSetting.getNormalOrderOvertime());
        if(CollectionUtil.isEmpty(orderDetails)){
            return count;
        }

        //修改订单状态为取消
        List<Long> ids = new ArrayList<>();
        for (OmsOrderDetail orderDetail : orderDetails) {
            ids.add(orderDetail.getId());
        }
        portalOrderDao.updateOrderStatus(ids,4);

        for (OmsOrderDetail orderDetail : orderDetails) {
            //解除订单商品库存锁定
            portalOrderDao.releaseSkuStockLock(orderDetail.getOrderItemList());
            //修改优惠券使用状态

            //返还使用积分

        }
        return null;
    }

    @Override
    public void cancelOrder(Long orderId) {

    }

    @Override
    public void sendDelayMessageCancelOrder(Long orderId) {

    }

    @Override
    public void confirmReceiveOrder(Long orderId) {

    }

    @Override
    public CommonPage<OmsOrderDetail> list(Integer status, Integer pageNum, Integer pageSize) {
        return null;
    }

    @Override
    public OmsOrderDetail detail(Long orderId) {
        return null;
    }

    @Override
    public void deleteOrder(Long orderId) {

    }
}
