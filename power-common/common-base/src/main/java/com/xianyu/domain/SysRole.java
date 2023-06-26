package com.xianyu.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @BelongsProject: powershop
 * @BelongsPackage: com.xianyu.domain
 * @Author: xianyu
 * @CreateTime: 2023-06-25  11:05
 * @Description: 角色类
 * @Version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("角色")
@TableName("sys_role")
public class SysRole implements Serializable {
    private static final long serialVersionUID = -5367948612063537495L;

    @TableId("role_id")
    private Long roleId;

    @TableField("role_name")
    private String roleName;

    @TableField("remark")
    private String remark;
    @TableField("create_user_id")
    private Long createUserId;
    @TableField("create_time")
    private Date createTime;

    @TableField(exist = false)
    private List<Long> menuIdList;
}
