package com.xianyu.controller;

import com.xianyu.domain.LoginSysUser;
import com.xianyu.model.Result;
import com.xianyu.utils.AuthUtil;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @BelongsProject: powershop
 * @BelongsPackage: com.xianyu.controller
 * @Author: xianyu
 * @CreateTime: 2023-06-20  16:02
 * @Description: 点击登录之后能够跳转至后台页面
 * @Version: 1.0
 */
@Api("后台用户Api")
@RestController
@RequestMapping("/sys/user")
public class SysUserController {
    @GetMapping ("/info")
    public Result<LoginSysUser> getUserInfo(){
        return Result.success(AuthUtil.getLoginSysUser());
    }
}
