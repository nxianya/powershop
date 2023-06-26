package com.xianyu.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
 * @CreateTime: 2023-06-22  09:40
 * @Description: 系统用户
 * @Version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_user")
public class SysUser implements Serializable {
    private static final long serialVersionUID = -3835735920970653090L;
    @TableId(value = "user_id", type = IdType.AUTO)
    private Long userId;
    @TableField("username")
    private String username;
    @TableField("`password`")
    private String password;
    @TableField("email")
    private String email;
    @TableField("mobile")
    private String mobile;
    @TableField("`status`")
    private Byte status;
    @TableField("create_user_id")
    private Long createUserId;
    @TableField("create_time")
    private Date createTime;
    @TableField("shop_id")
    private Long shopId;
    @TableField(exist = false)
    private List<Long> roleIdList;
}
