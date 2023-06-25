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
 * @CreateTime: 2023-06-24  10:18
 * @Description: 商品评论类
 * @Version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("prod_comm")
@ApiModel("商品评论")
public class ProdComm implements Serializable {
    private static final long serialVersionUID = -9218916062481630683L;
    // 【ID】
    @TableId(value = "prod_comm_id",type = IdType.AUTO)
    private Long prodCommId;

    // 【商品ID】
    @TableField("prod_id")
    private Long prodId;
    // 【商品的名字】
    @TableField("prod_name")
    private String prodName;
    // 【订单项ID】
    @TableField("order_item_id")
    private Long orderItemId;
    // 【评论用户ID】
    @TableField("open_id")
    private String openId;
    // 【评论内容】
    @TableField("content")
    private String content;
    // 【掌柜回复】
    @TableField("reply_content")
    private String replyContent;
    // 【记录时间】
    @TableField("create_time")
    private Date createTime;
    // 【回复时间】
    @TableField("reply_time")
    private Date replyTime;
    // 【 是否回复 0:未回复  1:已回复】
    @TableField("reply_sts")
    private Integer replySts;
    // 【IP来源】
    @TableField("postip")
    private String postip;
    // 【得分，0-5分】
    @TableField("score")
    private Byte score;
    // 【有用的计数】
    @TableField("useful_counts")
    private Integer usefulCounts;
    // 【晒图的json字符串】
    @TableField("pics")
    private String pics;
    // 【是否匿名(1:是  0:否)】
    @TableField("is_anonymous")
    private Integer isAnonymous;
    // 【是否显示，1:为显示，0:待审核， -1：不通过审核，不显示。如果需要审核评论，则是0,，否则1】
    @TableField("`status`")
    private Integer status;
    // 【评价(0:好评 1:中评 2:差评)】
    @TableField("evaluate")
    private Byte evaluate;
    // 【店铺】
    @TableField("shop_id")
    private Long shopId;

}
