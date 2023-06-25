package com.xianyu.controller;

import com.xianyu.domain.SysRole;
import com.xianyu.model.Result;
import com.xianyu.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
public class SysRoleController {

    @Autowired
    private SysRoleService SysRoleService;
    @GetMapping("/list")
    public Result<List<SysRole>> sysRoleInfo(){
        return Result.success(SysRoleService.list());
    }
}
