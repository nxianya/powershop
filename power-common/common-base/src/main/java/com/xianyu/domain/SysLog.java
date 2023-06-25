package com.xianyu.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @BelongsProject: powershop
 * @BelongsPackage: com.xianyu.domain
 * @Author: xianyu
 * @CreateTime: 2023-06-22  11:42
 * @Description: 日志类,记录每次操作
 * @Version: 1.0
 */
@TableName("sys_log")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("操作日志类")
// 【@Builder:生成对象,并可为对象进行链式赋值】
/*
    它作用于类，将其变成建造者模式
    可以以链的形式调用
    初始化实例对象生成的对象是不可以变的，可以在创建对象的时候进行赋值
    如果需要在原来的基础上修改可以加 set 方法，final 字段可以不需要初始化
    它会生成一个全参的构造函数
 */
@Builder
public class SysLog implements Serializable {
    private static final long serialVersionUID = 3204996232887613805L;
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    @TableField("user_id")
    private Long userId;
    @TableField("`operation`")
    private String operation;
    @TableField("`method`")
    private String method;
    @TableField("params")
    private String params;
    @TableField("time")
    private Long time;
    @TableField("ip")
    private String ip;
    @TableField("create_date")
    private Date createDate;
}
