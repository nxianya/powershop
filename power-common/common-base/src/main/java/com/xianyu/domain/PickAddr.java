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
 * @CreateTime: 2023-06-26  19:33
 * @Description: 用户配送地址类
 * @Version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("pick_addr")
@ApiModel("用户配送地址类")
public class PickAddr implements Serializable {
    private static final long serialVersionUID = -7629809150889216964L;
    @TableId("addr_id")
    private Long addrId;
    @TableField("addr_name")
    private String addrName;
    @TableField("addr")
    private String addr;
    @TableField("mobile")
    private String mobile;
    @TableField("province_id")
    private Long provinceId;
    @TableField("province")
    private String province;
    @TableField("city_id")
    private Long cityId;
    @TableField("city")
    private String city;
    @TableField("area_id")
    private Long areaId;
    @TableField("area")
    private String area;
    @TableField("shop_id")
    private Long shopId;

}
