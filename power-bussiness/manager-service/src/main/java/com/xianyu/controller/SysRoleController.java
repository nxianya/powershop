package com.xianyu.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xianyu.domain.SysRole;
import com.xianyu.model.Result;
import com.xianyu.service.SysRoleService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * @BelongsProject: powershop
 * @BelongsPackage: com.xianyu.controller
 * @Author: xianyu
 * @CreateTime: 2023-06-25  11:15
 * @Description: TODO 加缓存
 * @Version: 1.0
 */
@RestController
@RequestMapping("/sys/role")
@Slf4j
public class SysRoleController {

    @Autowired
    private SysRoleService SysRoleService;
    @GetMapping("/list")
    public Result<List<SysRole>> sysRoleInfo(){
        return Result.success(SysRoleService.list());
    }

    @GetMapping("/page")
    @ApiOperation("查询角色权限分页列表")
    public Result<Page<SysRole>> loadSysRolePage(Page<SysRole> page,SysRole sysRole){
        return Result.success(SysRoleService.loadSysRolePage(page,sysRole));
    }

    @PostMapping
    @ApiOperation("添加角色")
    public Result<String> addSysRole(@RequestBody SysRole sysRole){
        return SysRoleService.addSysRole(sysRole)>0? Result.success("添加成功"): Result.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(), "添加失败");
    }

    @GetMapping("/info/{id}")
    @ApiOperation("根据Id查询角色信息")
    public Result<SysRole> selectSysRoleById(@PathVariable("id") Long id){
        return Result.success(SysRoleService.selectSysRoleById(id));
    }

    @PutMapping
    @ApiOperation("编辑角色详情")
    public Result<Integer> updateSysRoleInfo(@RequestBody SysRole sysRole){
        return Result.success(SysRoleService.updateSysRoleInfo(sysRole));
    }

    @DeleteMapping
    @ApiOperation("删除角色及权限信息")
    public Result<Integer> deleteSysRole(@RequestBody List<Long> ids){
        // log.info("ids:{}",ids);
        return Result.success(SysRoleService.deleteSysRole(ids));
    }
}
