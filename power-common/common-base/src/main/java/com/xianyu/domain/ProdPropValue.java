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

/**
 * @BelongsProject: powershop
 * @BelongsPackage: com.xianyu.domain
 * @Author: xianyu
 * @CreateTime: 2023-06-24  10:50
 * @Description: 产品属性类
 * @Version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("产品属性")
@TableName("prod_prop_value")
public class ProdPropValue implements Serializable {
    private static final long serialVersionUID = 8036411564034411873L;
    // 【属性值ID】
    @TableId(value = "value_id",type = IdType.AUTO)
    private Long valueId;
    // 【属性值名称】
    @TableField("prop_value")
    private String propValue;
    // 【属性ID】
    @TableField("prop_id")
    private Long propId;

}
