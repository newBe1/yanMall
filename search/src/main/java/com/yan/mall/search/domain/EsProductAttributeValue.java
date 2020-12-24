package com.yan.mall.search.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * Description:搜索系统中商品属性
 * User: Ryan
 * Date: 2020-12-23
 * Time: 13:52
 */
@Data
public class EsProductAttributeValue implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键id")
    private Long id;

    @ApiModelProperty("商品属性id")
    private Long productAttributeId;

    @Field(type = FieldType.Keyword)
    @ApiModelProperty("商品属性值")
    private String value;

    @ApiModelProperty("商品属性类型: 0->规格；1->参数")
    private Integer type;

    @Field(type = FieldType.Keyword)
    @ApiModelProperty("商品属性名称")
    private String name;


}
