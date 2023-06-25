package com.xianyu.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

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
public class SysRole {
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
}
