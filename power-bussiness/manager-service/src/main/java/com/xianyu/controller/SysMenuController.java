package com.xianyu.controller;

import com.xianyu.aop.MyLog;
import com.xianyu.domain.SysMenu;
import com.xianyu.model.Result;
import com.xianyu.service.SysMenuService;
import com.xianyu.utils.AuthUtil;
import com.xianyu.vo.MenuAndAuth;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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
    private SysMenuService sysMenuService;

    @GetMapping("/nav")
    @PreAuthorize("hasAuthority('sys:menu:list')")
    @ApiOperation("获取当前用户的权限和菜单")
    @MyLog(operation = "获取菜单和权限的集合")
    public Result<MenuAndAuth> getMenuAndAuths(){
        MenuAndAuth menuAndAuth = new MenuAndAuth();
        menuAndAuth.setAuthorities(AuthUtil.getSysUserAuth());
        menuAndAuth.setMenuList(sysMenuService.getSysMenusByUserId(AuthUtil.getSysUserId()));
        return Result.success(menuAndAuth);
    }

    @GetMapping("/table")
    @ApiOperation("查询角色权限菜单")
    public Result<List<SysMenu>> sysMenuResult(){
        return Result.success(sysMenuService.list());
    }

    @GetMapping("/list")
    @ApiOperation("查询所有权限列表")
    public Result<List<SysMenu>> getAllSysMenu(){
        return Result.success(sysMenuService.list());
    }

    @GetMapping("/info/{id}")
    @ApiOperation("根据Id查询权限详情")
    public Result<SysMenu> selectSysMenu(@PathVariable Long id){
        return Result.success(sysMenuService.selectSysMenu(id));
    }

    @PostMapping
    @ApiOperation("新增权限")
    public Result<String> addSysMenu(@RequestBody SysMenu sysMenu){
        return sysMenuService.save(sysMenu)? Result.success("添加成功"): Result.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(), "添加失败");
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除权限")
    public Result<String> deleteMenu(@PathVariable("id") Long id){
        return sysMenuService.removeById(id)?Result.success("删除成功"): Result.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(), "删除失败");
    }

    @PutMapping
    @ApiOperation("修改权限")
    public Result<String> updateSysMenu(@RequestBody SysMenu sysMenu){
        return sysMenuService.updateById(sysMenu)?Result.success("修改成功"):Result.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(), "修改失败");
    }
}
