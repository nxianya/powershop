package com.xianyu.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @BelongsProject: powershop
 * @BelongsPackage: com.xianyu.model
 * @Author: xianyu
 * @CreateTime: 2023-06-15  11:28
 * @Description: 返回结果类
 * @Version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {
    // 【响应的状态码,默认为200】
    private Integer code = 200;
    // 【响应消息文本】
    private String msg;
    // 【数据内容】
    private T data;

    // 【成功返回-带参】
    public static <T> Result<T> success(T data) {
        return success("ok", data);
    }

    //【不带数据-成功】
    public static <T> Result<T> success(String msg) {
        return success(msg, null);
    }

    //【不带数据与参数-成功】
    public static <T> Result<T> success() {
        return success("ok", null);
    }

    //【不带数据-传入boolean】
    public static <T> Result<T> success(Boolean flag) {
        return flag ? success("ok") : fail(500, "服务器异常");
    }


    // 【成功结果】
    public static <T> Result<T> success(String msg, T data) {
        Result<T> result = new Result<>();
        result.setMsg(msg);
        result.setData(data);
        return result;
    }

    //【失败结果】
    public static <T> Result<T> fail(Integer code, String msg) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }
}
