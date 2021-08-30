package com.yan.mall.search.controller;

import com.yan.mall.common.api.CommonPage;
import com.yan.mall.common.api.CommonResult;
import com.yan.mall.search.domain.EsProduct;
import com.yan.mall.search.repository.EsProductRepository;
import com.yan.mall.search.service.EsProductService;
import io.swagger.annotations.*;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Ryan
 * Date: 2020-12-23
 * Time: 15:37
 */
@RestController
@Api(tags = "搜索商品管理")
@RequestMapping("/esProduct")
public class EsProductController {

    @Resource
    private EsProductService productService;

    @Resource
    private EsProductRepository esProductRepository;

    @ApiOperation(value = "导入商品信息到ES")
    @RequestMapping(value = "/importAllProduct", method = RequestMethod.PUT)
    public CommonResult<Integer> importAllProduct() {
        return CommonResult.success(productService.importAll());
    }

    @ApiOperation(value = "根据id删除ES中单个商品信息")
    @RequestMapping(value = "deleteProductById/{id}", method = RequestMethod.POST)
    public CommonResult deleteProductById(@PathVariable Long id) {
        productService.delete(id);
        return CommonResult.success(null);
    }

    @ApiOperation(value = "根据id查询商品信息")
    @RequestMapping(value = "selectProductById/{id}", method = RequestMethod.GET)
    public CommonResult selectProductById(@PathVariable Long id) {
        return CommonResult.success(productService.select(id));
    }

    @ApiOperation(value = "根据ids批量删除ES中商品信息")
    @RequestMapping(value = "deleteProductById/batch", method = RequestMethod.POST)
    public CommonResult deleteProductById(@RequestParam("ids") List<Long> ids) {
        productService.delete(ids);
        return CommonResult.success(null);
    }

    @ApiOperation(value = "根据id创建商品")
    @RequestMapping(value = "/create/{id}", method = RequestMethod.POST)
    public CommonResult<EsProduct> create(@PathVariable Long id) {
        EsProduct esProduct = productService.create(id);
        if (esProduct != null) {
            return CommonResult.success(esProduct);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation(value = "简单搜索")
    @RequestMapping(value = "/search/simple", method = RequestMethod.GET)
    public CommonResult<CommonPage<EsProduct>> search(@RequestParam(required = false) String keyword,
                                                      @RequestParam(required = false, defaultValue = "0") Integer pageNum,
                                                      @RequestParam(required = false, defaultValue = "5") Integer pageSize) {
        Page<EsProduct> esProductPage = productService.search(keyword, pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(esProductPage));
    }

    @ApiOperation(value = "综合搜索、筛选、排序")
    @ApiImplicitParam(name = "sort", value = "排序字段:0->按相关度；1->按新品；2->按销量；3->价格从低到高；4->价格从高到低",
            defaultValue = "0", allowableValues = "0,1,2,3,4", paramType = "query", dataType = "integer")
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public CommonResult<CommonPage<EsProduct>> search(@RequestParam(required = false,name = "keyword",value = "关键字") String keyword,
                                                      @RequestParam(required = false) Long brandId,
                                                      @RequestParam(required = false) Long productCategoryId,
                                                      @RequestParam(required = false, defaultValue = "0") Integer pageNum,
                                                      @RequestParam(required = false, defaultValue = "5") Integer pageSize,
                                                      @RequestParam(required = false, defaultValue = "0") Integer sort) {
        Page<EsProduct> esProductPage = productService.search(keyword, brandId, productCategoryId, pageNum, pageSize, sort);
        return CommonResult.success(CommonPage.restPage(esProductPage));
    }


    @RequestMapping(value = "test", method = RequestMethod.POST)
    public CommonResult test(int lowPrice , int highPrice , String brandName){
        return CommonResult.success(esProductRepository.findByPriceBetweenAndBrandName(new BigDecimal(lowPrice), new BigDecimal(highPrice), brandName));
    }


}
