package com.xianyu.controller;

import com.xianyu.aop.MyLog;
import com.xianyu.model.Result;
import com.xianyu.service.SysMenuService;
import com.xianyu.utils.AuthUtil;
import com.xianyu.vo.MenuAndAuth;
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
 * @CreateTime: 2023-06-20  11:45
 * @Description: 菜单接口
 * @Version: 1.0
 */
@RestController
@Api(tags = "菜单接口")
@RequestMapping("/sys/menu")
public class SysMenuController {

    @Autowired
    private SysMenuService service;

    @GetMapping("/nav")
    @PreAuthorize("hasAuthority('sys:menu:list')")
    @ApiOperation("获取当前用户的权限和菜单")
    @MyLog(operation = "获取菜单和权限的集合")
    public Result<MenuAndAuth> getMenuAndAuths(){
        MenuAndAuth menuAndAuth = new MenuAndAuth();
        menuAndAuth.setAuthorities(AuthUtil.getSysUserAuth());
        menuAndAuth.setMenuList(service.getSysMenusByUserId(AuthUtil.getSysUserId()));
        return Result.success(menuAndAuth);
    }

}
