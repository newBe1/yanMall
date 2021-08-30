package com.yan.mall.search.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.yan.mall.search.dao.EsProductDao;
import com.yan.mall.search.domain.EsProduct;
import com.yan.mall.search.domain.EsProductRelatedInfo;
import com.yan.mall.search.repository.EsProductRepository;
import com.yan.mall.search.service.EsProductService;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Ryan
 * Date: 2020-12-23
 * Time: 14:32
 */
@Service
public class EsProductServiceImpl implements EsProductService {
    private static final Logger LOGGER = LoggerFactory.getLogger(EsProductServiceImpl.class);

    @Resource
    private EsProductDao productDao;
    @Autowired
    private EsProductRepository productRepository;
    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Override
    public int importAll() {
        List<EsProduct> productList = productDao.getAllEsProductList(null);
        Iterable<EsProduct> esProductIterable = productRepository.saveAll(productList);
        Iterator<EsProduct> iterator = esProductIterable.iterator();
        int result = 0;
        while (iterator.hasNext()){
            result ++;
            iterator.next();
        }
        return result;
    }

    @Override
    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public EsProduct create(Long id) {
        EsProduct result = null;
        List<EsProduct> productList = productDao.getAllEsProductList(id);
        if(productList.size()>0){
            EsProduct product = productList.get(0);
            result = productRepository.save(product);
        }

        return result;
    }

    @Override
    public void delete(List<Long> ids) {
        if(!CollectionUtil.isEmpty(ids)){
            List<EsProduct> productList = new ArrayList<>();
            for (Long id : ids) {
                EsProduct product = new EsProduct();
                product.setId(id);
                productList.add(product);
            }
            productRepository.deleteAll(productList);
        }
    }

    @Override
    public Page<EsProduct> search(String keyword, Integer pageNum, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        return productRepository.findByNameOrSubTitleOrKeywords(keyword,keyword,keyword,pageable);
    }

    @Override
    public Page<EsProduct> search(String keyword, Long brandId, Long productCategoryId, Integer pageNum, Integer pageSize, Integer sort) {
        return null;
    }

    @Override
    public Page<EsProduct> recommend(Long id, Integer pageNum, Integer pageSize) {
        return null;
    }

    @Override
    public EsProductRelatedInfo searchRelatedInfo(String keyword) {
        return null;
    }

    @Override
    public Optional<EsProduct> select(Long id) {
        //term 关键字查询  通常用于数字，日期和枚举等结构化数据，而不是全文本字段

        return productRepository.findById(id);
    }

    /*public List<EsProduct> customizeSearch(EsProduct esProduct){
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();


        if(!StrUtil.isEmpty(esProduct.getBrandName())) boolQueryBuilder.must(QueryBuilders.matchQuery("brandName",esProduct.getBrandName()));


    }*/

}
