package com.xianyu.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xianyu.aop.MyLog;
import com.xianyu.domain.LoginSysUser;
import com.xianyu.domain.SysUser;
import com.xianyu.model.Result;
import com.xianyu.service.SysUserService;
import com.xianyu.utils.AuthUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
@Api(tags = "后台用户Api")
@RestController
@RequestMapping("/sys/user")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    @GetMapping ("/info")
    // 【通过权限方法获取某一个字符串里的权限】
    @PreAuthorize("hasAuthority('sys:user:info')")
    @ApiOperation("获取当前登录用户的信息")
    @MyLog(operation="获取当前登录用户的信息")
    public Result<LoginSysUser> getUserInfo(){
        return Result.success(AuthUtil.getLoginSysUser());
    }

    @GetMapping("/page")
    @ApiOperation("获取系统管理员列表数据")
    @MyLog(operation="分页加载管理员的列表")
    public Result<Page<SysUser>> loadSysUserPage(Page<SysUser> page,SysUser sysUser){
        return Result.success(sysUserService.loadSysUserPage(page,sysUser));
    }
}
