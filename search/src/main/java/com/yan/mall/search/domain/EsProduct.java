package com.yan.mall.search.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * Description:搜索系统中 存储的商品信息
 * User: Ryan
 * Date: 2020-12-23
 * Time: 11:56
 *
 * //标示映射到Elasticsearch文档上的领域对象
 * public @interface Document {
 *   //索引库名次，mysql中数据库的概念
 *     String indexName();
 *   //文档类型，mysql中表的概念
 *     String type() default "";
 *   //默认分片数
 *     short shards() default 5;
 *   //默认副本数量
 *     short replicas() default 1;
 * }
 *
 * public @interface Field {
 *   //文档中字段的类型
 *     FieldType type() default FieldType.Auto;
 *   //是否建立倒排索引
 *     boolean index() default true;
 *   //是否进行存储
 *     boolean store() default false;
 *   //分词器名次
 *     String analyzer() default "";
 * }
 *
 * //表示是文档的id，文档可以认为是mysql中表行的概念
 * public @interface Id {
 * }
 *
 */
@Document(indexName = "pms",indexStoreType = "product",shards = 1,replicas = 0,type = "_doc")
@Data
public class EsProduct implements Serializable {
    private static final long serialVersionUID = -1L;

    @Id
    @ApiModelProperty("主键id")
    private Long id;

    @Field(type = FieldType.Keyword)
    @ApiModelProperty("货号")
    private String productSn;

    @ApiModelProperty("品牌id")
    private Long brandId;

    @Field(type = FieldType.Keyword)
    @ApiModelProperty("品牌名")
    private String brandName;

    @ApiModelProperty("类别id")
    private Long productCategoryId;

    @Field(type = FieldType.Keyword)
    @ApiModelProperty("商品类别名称")
    private String productCategoryName;

    @ApiModelProperty("图片")
    private String pic;

    @Field(type = FieldType.Text,analyzer = "ik_max_word")
    @ApiModelProperty("商品名称")
    private String name;

    @Field(type = FieldType.Text,analyzer = "ik_max_word")
    @ApiModelProperty("副标题")
    private String subTitle;

    @Field(type = FieldType.Text,analyzer = "ik_max_word")
    @ApiModelProperty("关键字")
    private String keywords;

    @ApiModelProperty("价格")
    private BigDecimal price;

    @ApiModelProperty("销量")
    private Integer sale;

    @ApiModelProperty("新品状态:0->不是新品；1->新品")
    private Integer newStatus;

    @ApiModelProperty("推荐状态:0->不推荐；1->推荐")
    private Integer recommendStatus;

    @ApiModelProperty("库存")
    private Integer stock;

    @ApiModelProperty("促销类型：0->没有促销使用原价;1->使用促销价；2->使用会员价；3->使用阶梯价格；4->使用满减价格；5->限时购")
    private Integer promotionType;

    @ApiModelProperty("排序")
    private Integer sort;

    @Field(type =FieldType.Nested)
    @ApiModelProperty("属性值")
    private List<EsProductAttributeValue> attributeValueList;
}
