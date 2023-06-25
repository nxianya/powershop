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

/**
 * @BelongsProject: powershop
 * @BelongsPackage: com.xianyu.domain
 * @Author: xianyu
 * @CreateTime: 2023-06-24  11:20
 * @Description: 单品SKU表
 * @Version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("单品SKU类")
@TableName("sku")
public class Sku implements Serializable {

    private static final long serialVersionUID = -8161615647003386564L;
    // 【单品ID】
    @TableId(value = "sku_id",type = IdType.AUTO)
    private Long skuId;
    // 【商品ID】
    @TableField("prod_id")
    private Long prodId;
    // 【销售属性组合字符串 格式是p1:v1;p2:v2】
    @TableField("properties")
    private String properties;
    // 【原价】
    @TableField("ori_price")
    private BigDecimal oriPrice;
    // 【价格】
    @TableField("price")
    private BigDecimal price;
    // 【商品在付款减库存的状态下，该sku上未付款的订单数量】
    @TableField("stocks")
    private Integer stocks;
    // 【实际库存】
    @TableField("actual_stocks")
    private Integer actualStocks;
    // 【修改时间】
    @TableField("update_time")
    private Date updateTime;
    // 【记录时间】
    @TableField("create_time")
    private Date createTime;
    // 【商家编码】
    @TableField("party_code")
    private String partyCode;
    // 【商品条形码】
    @TableField("model_id")
    private String model_id;

    // 【sku图片】
    @TableField("pic")
    private String pic;
    // 【sku名称】
    @TableField("sku_name")
    private String skuName;
    // 【商品名称】
    @TableField("prod_name")
    private String prodName;
    // 【版本号】
    @TableField("version")
    private Integer version;
    // 【商品重量】
    @TableField("weight")
    private Double weight;
    // 【商品体积】
    @TableField("volume")
    private Double volume;
    // 【0 禁用 1 启用】
    @TableField("`status`")
    private Byte status;
    // 【0 正常 1 已被删除】
    @TableField("is_delete")
    private Byte isDelete;
}
