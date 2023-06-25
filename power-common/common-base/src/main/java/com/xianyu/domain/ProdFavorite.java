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
 * @CreateTime: 2023-06-24  10:37
 * @Description: 商品收藏类
 * @Version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("prod_favorite")
@ApiModel("商品收藏")
public class ProdFavorite implements Serializable {
    private static final long serialVersionUID = -5343953664903274826L;
    // 【ID】
    @TableId(value = "favorite_id",type = IdType.AUTO)
    private Long favoriteId;

    // 【商品ID】
    @TableField("prod_id")
    private Long prodId;
    // 【收藏时间】
    @TableField("rec_time")
    private Date recTime;
    // 【用户ID】
    @TableField("user_id")
    private String userId;
}
