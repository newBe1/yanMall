package com.yan.mall.portal.dao;

import com.yan.mall.model.OmsOrderItem;
import com.yan.mall.portal.domain.OmsOrderDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Ryan
 * Date: 2020-11-26
 * Time: 17:23
 */
@Mapper
public interface PortalOrderDao {
    /**
     * 查询超时、未支付订单
     * @return
     */
    List<OmsOrderDetail> getTimeOutOrders(@Param("minute")Integer minute);

    /**
     * 批量更新订单状态
     * @param ids
     * @param status
     * @return
     */
    int updateOrderStatus(@Param("ids") List<Long> ids, @Param("status") int status);

    int releaseSkuStockLock(@Param("orderItemList") List<OmsOrderItem> orderItemList);
}
