package com.yan.mall.search;

import com.yan.mall.search.domain.EsProduct;
import com.yan.mall.search.repository.EsProductRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 * @description: es单元测试
 * @author: Ryan
 * @create: 2021-06-02 14:17
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class EsTest {

    @Resource
    private EsProductRepository esProductRepository;

    @Test
    public void test1(){

        Pageable pageable = PageRequest.of(1, 6);
        Page<EsProduct> list = esProductRepository.findByNameOrSubTitleOrKeywords("ds","fa","fas",pageable);

        for (EsProduct esProduct : list) {
            System.out.println("------"+esProduct);
        }
    }

}
