package com.yan.mall.search.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @description:
 * @author: Ryan
 * @create: 2021-06-09 14:53
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EsProductPO {

    @ApiModelProperty("品牌名")
    private String brandName;

    @ApiModelProperty("商品属性值")
    private List<String> value;

    @ApiModelProperty("库存")
    private Integer stock;
}
