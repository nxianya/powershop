package com.xianyu.domain;

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
 * @CreateTime: 2023-06-26  20:00
 * @Description: 地区表
 * @Version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("area")
@ApiModel("地区表")
public class Area implements Serializable {
    private static final long serialVersionUID = -6820956412315971706L;
    @TableId("area_id")
    private Long areaId;
    @TableField("area_name")
    private String areaName;
    @TableField("parent_id")
    private Long parentId;
    @TableField("level")
    private Integer level;

}
