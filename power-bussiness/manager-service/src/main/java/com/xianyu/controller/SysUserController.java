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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.Arrays;

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
@Slf4j
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

    @PostMapping
    @ApiOperation("新增管理员")
    @MyLog(operation = "新增管理员")
    public Result<String> addSysUser(@RequestBody SysUser sysUser){
        // log.info("SysUser:{}",sysUser);
        return sysUserService.addSysUser(sysUser)?
                Result.success("添加成功"):Result.fail(HttpStatus.FORBIDDEN.value(),"添加失败");
    }

    @GetMapping("/info/{sysUserId}")
    @ApiOperation("查询管理员信息")
    @MyLog(operation = "查询管理员信息")
    public Result<SysUser> sysUserInfo(@PathVariable("sysUserId") Long id){
        return Result.success(sysUserService.selectSysUserById(id));
    }

    @PutMapping
    @ApiOperation("修改管理员信息")
    @MyLog(operation = "修改管理员信息")
    public Result<String> updateUserInfo(@RequestBody SysUser sysUser){
        return sysUserService.updateUserInfo(sysUser)?
                Result.success("修改成功"):Result.fail(HttpStatus.FORBIDDEN.value(),"修改失败");
    }

    @DeleteMapping("/{ids}")
    public Result<String> deleteUserByIds(@PathVariable("ids") Long[] ids){
        // log.info("ids:{}", Arrays.toString(ids));
        return sysUserService.deleteUserByIds(Arrays.asList(ids))?
                Result.success("删除成功"):Result.fail(HttpStatus.FORBIDDEN.value(),"删除失败");
    }

}
