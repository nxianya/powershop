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
import java.util.Date;

/**
 * @BelongsProject: powershop
 * @BelongsPackage: com.xianyu.domain
 * @Author: xianyu
 * @CreateTime: 2023-06-24  11:04
 * @Description: 商品店铺中间表
 * @Version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("prod_tag_reference")
@ApiModel("商品店铺中间表")
public class ProdTagReference implements Serializable {
    private static final long serialVersionUID = 7798844976540090862L;
    // 【分组引用id】
    @TableId(value = "reference_id",type = IdType.AUTO)
    private Long referenceId;
    // 【店铺id】
    @TableField("shop_id")
    private Long shopId;
    // 【标签id】
    @TableField("tag_id")
    private Long tagId;
    // 【商品id】
    @TableField("prod_id")
    private Long prodId;
    // 【状态(1:正常,0:删除)】
    @TableField("`status`")
    // private Boolean status;
    private Byte status;

    // 【创建时间】
    @TableField("create_time")
    private Date createTime;

}
