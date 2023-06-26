package com.xianyu.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @BelongsProject: powershop
 * @BelongsPackage: com.xianyu.domain
 * @Author: xianyu
 * @CreateTime: 2023-06-24  09:47
 * @Description: 商品类
 * @Version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("prod")
@ApiModel("商品类")
public class Prod implements Serializable {
    // 【产品ID】
    @TableId(value = "prod_id",type = IdType.AUTO)
    private Long prodId;
    // 【商品名称】
    @TableField("prod_name")
    private String prodName;
    // 【店铺id】
    @TableField("shop_id")
    private Long shopId;
    // 【原价】
    @TableField("ori_price")
    private BigDecimal oriPrice;
    // 【现价】
    @TableField("price")
    private BigDecimal price;
    // 【简要描述,卖点等】
    @TableField("brief")
    private String brief;
    // 【详细描述】
    @TableField("content")
    private String content;
    // 【商品主图】
    @TableField("pic")
    private String pic;
    // 【商品图片，以,分割】
    @TableField("imgs")
    private String imgs;
    // 【默认是1，表示正常状态, -1表示删除, 0下架】
    @TableField("`status`")
    private Integer status;
    // 【商品分类】
    @TableField("category_id")
    private Long categoryId;
    // 【销量】
    @TableField("sold_num")
    private Integer soldNum;
    // 【总库存】
    @TableField("total_stocks")
    private Integer totalStocks;
    // 【配送方式json见TransportModeVO】
    @TableField("delivery_mode")
    private String deliveryMode;
    // 【运费模板id】
    @TableField("delivery_template_id")
    private Long deliveryTemplateId;
    // 【录入时间】
    @TableField("create_time")
    private Date createTime;
    // 【修改时间】
    @TableField("update_time")
    private Date updateTime;
    // 【上架时间】
    @TableField("putaway_time")
    private Date putawayTime;
    // 【版本 乐观锁】
    @TableField("version")
    private Integer version;
    // @TableField(exist = false)
    // private List<Long> tagList;
    //
    // @TableField(exist = false)
    // private List<Sku> skuList;
}
