package com.yan.mall.search.dao;

import com.yan.mall.search.domain.EsProduct;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Ryan
 * Date: 2020-12-23
 * Time: 14:33
 */
public interface EsProductDao {
    /**
     * 获取指定ID的搜索商品
     */
    List<EsProduct> getAllEsProductList(@Param("id") Long id);

}
