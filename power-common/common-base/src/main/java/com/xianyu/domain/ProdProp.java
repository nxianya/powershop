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
import java.util.List;

/**
 * @BelongsProject: powershop
 * @BelongsPackage: com.xianyu.domain
 * @Author: xianyu
 * @CreateTime: 2023-06-24  10:43
 * @Description: 产品信息类
 * @Version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("prod_prop")
@ApiModel("产品信息")
public class ProdProp implements Serializable {
    private static final long serialVersionUID = -9132516837107144351L;
    // 【属性id】
    @TableId(value = "prop_id",type = IdType.AUTO)
    private Long propId;
    // 【属性名称】
    @TableField("prop_name")
    private String propName;
    // 【ProdPropRule 1:销售属性(规格); 2:参数属性】
    @TableField("`rule`")
    private Integer rule;
    // 【店铺id】
    @TableField("shop_id")
    private Long shopId;

    @TableField(exist = false)
    private List<ProdPropValue> prodPropValues;
}
