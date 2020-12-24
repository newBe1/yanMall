package com.yan.mall.search.domain;

import lombok.Data;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description: 搜索系统中商品的品牌名称、分类名称、属性
 * User: Ryan
 * Date: 2020-12-23
 * Time: 14:19
 */
@Data
public class EsProductRelatedInfo {
    private List<String> brandNameList;
    private List<String> productCategoryNameList;
    private List<ProductAttr>   productAttrList;

    @Data
    public static class ProductAttr{
        private Long attrId;
        private String attrName;
        private List<String> attrValues;
    }
}
