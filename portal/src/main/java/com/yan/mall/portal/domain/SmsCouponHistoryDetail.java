package com.yan.mall.portal.domain;

import com.yan.mall.model.SmsCoupon;
import com.yan.mall.model.SmsCouponHistory;
import com.yan.mall.model.SmsCouponProductCategoryRelation;
import com.yan.mall.model.SmsCouponProductRelation;
import lombok.Data;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Ryan
 * Date: 2020-11-26
 * Time: 16:25
 */
@Data
public class SmsCouponHistoryDetail extends SmsCouponHistory {
    //相关优惠券信息
    private SmsCoupon coupon;
    //优惠券关联商品
    private List<SmsCouponProductRelation> productRelationList;
    //优惠券关联商品分类
    private List<SmsCouponProductCategoryRelation> categoryRelationList;
}
