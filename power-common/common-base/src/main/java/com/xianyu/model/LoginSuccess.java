package com.xianyu.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @BelongsProject: powershop
 * @BelongsPackage: com.xianyu.model
 * @Author: xianyu
 * @CreateTime: 2023-06-16  10:27
 * @Description: 登录成功后配置的属性
 * @Version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginSuccess {
    // 【Token】
    private String accessToken;
    // 【过期时间】
    private String expiresIn;
    // 【认证类型】
    private String type;

}
