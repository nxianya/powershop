package com.xianyu.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @BelongsProject: powershop
 * @BelongsPackage: com.xianyu.domain
 * @Author: xianyu
 * @CreateTime: 2023-06-20  09:45
 * @Description: 菜单实体类
 * @Version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("菜单实体类")
@TableName(value = "sys_menu")
public class SysMenu implements Serializable {
    @TableId(value = "menu_id",type = IdType.AUTO)
    private Long menuId;
    @TableField(value = "parent_id")
    private Long parentId;
    @TableField(value = "`name`")
    private String name;
    @TableField(value = "url")
    private String url;
    // 【授权(使用多个逗号把不同的权限隔开)】
    @TableField(value = "perms")
    private String perms;
    // 【类型:0-目录 1-菜单 2-按钮】
    @TableField(value = "`type`")
    private Integer type;
    @TableField(value = "icon")
    private String icon;
    // 【排序】
    @TableField(value = "order_num")
    private Integer orderNum;


    @ApiModelProperty(value="子菜单的集合")
    @TableField(exist = false)
    private List<SysMenu> list;
}
