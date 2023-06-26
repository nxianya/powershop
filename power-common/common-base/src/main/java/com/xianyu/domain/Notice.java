package com.xianyu.domain;

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
 * @CreateTime: 2023-06-26  15:23
 * @Description: 公告类
 * @Version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("notice")
@ApiModel("公告类")
public class Notice implements Serializable {
    private static final long serialVersionUID = 4674812660370091755L;
    @TableId("id")
    public Long id;
    @TableField("shop_id")
    private Long shopId;
    @TableField("title")
    private String title;
    @TableField("content")
    private String content;
    @TableField("status")
    private Byte status;
    @TableField("is_top")
    private Byte isTop;
    @TableField("create_time")
    private Date createTime;
    @TableField("update_time")
    private Date updateTime;
}
