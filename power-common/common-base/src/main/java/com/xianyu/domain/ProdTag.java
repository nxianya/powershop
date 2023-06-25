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
 * @CreateTime: 2023-06-24  10:56
 * @Description: 商品分组表
 * @Version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("商品分组")
@TableName("prod_tag")
public class ProdTag implements Serializable {
    // 【分组标签id】
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    // 【分组标题】
    @TableField("title")
    private String title;
    // 【状态(1为正常,0为删除)】
    @TableField("`status`")
    private Boolean status;
    // 【列表样式(0:一列一个,1:一列两个,2:一列三个)】
    @TableField("`style`")
    private Integer style;
    // 【排序】
    @TableField("seq")
    private Integer seq;
    // 【创建时间】
    @TableField("create_time")
    private Date createTime;
    // 【修改时间】
    @TableField("update_time")
    private Date updateTime;


}
