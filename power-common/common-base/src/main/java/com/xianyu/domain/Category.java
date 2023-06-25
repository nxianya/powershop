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
 * @CreateTime: 2023-06-24  09:29
 * @Description: 产品类目类
 * @Version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("category")
@ApiModel("产品类目")
public class Category implements Serializable {

    private static final long serialVersionUID = 2022855723382097873L;
    // 【类目ID】
    @TableId(value = "category_id",type = IdType.AUTO)
    private Long categoryId;
    // 【父节点】
    @TableField("parent_id")
    private Long parentId;
    // 【产品类目名称】
    @TableField("category_name")
    private String categoryName;
    // 【类目图标】
    @TableField("icon")
    private String icon;
    // 【类目的显示图片】
    @TableField("pic")
    private String pic;
    // 【排序】
    @TableField("seq")
    private Integer seq;
    // 【默认是1，表示正常状态,0为下线状态】
    @TableField("`status`")
    private Integer status;
    // 【记录时间】
    @TableField("create_time")
    private Date createTime;
    // 【更新时间】
    @TableField("update_time")
    private Date updateTime;

}