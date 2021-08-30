package com.yan.mall.search.repository;

import com.yan.mall.search.domain.EsProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Ryan
 * Date: 2020-12-23
 * Time: 14:41
 */
public interface EsProductRepository extends ElasticsearchRepository<EsProduct, Long> {


    //自定义方法名查询   查询语法：findBy+字段名+Keyword+字段名+....


    /**
     * 搜索查询
     *
     * @param name              商品名称
     * @param subTitle          商品标题
     * @param keywords          商品关键字
     * @param page              分页信息
     * @return
     */
    Page<EsProduct> findByNameOrSubTitleOrKeywords(String name, String subTitle, String keywords,Pageable page);


    List<EsProduct> findByPriceBetweenAndBrandName(BigDecimal lowPrice , BigDecimal highPrice , String brandName);

}
