package com.xianyu.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xianyu.domain.SysLog;
import com.xianyu.model.Result;
import com.xianyu.service.SysLogService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @BelongsProject: powershop
 * @BelongsPackage: com.xianyu.controller
 * @Author: xianyu
 * @CreateTime: 2023-06-24  15:24
 * @Description: 操作日志接口
 * @Version: 1.0
 */
@RestController
@RequestMapping("/sys/log")
@Api(tags = "操作日志接口")
public class SysLogController {
    @Autowired
    private SysLogService sysLogService;

    @GetMapping("/page")
    public Result<Page<SysLog>> page(Page<SysLog> page,SysLog sysLog){
        return Result.success(sysLogService.loadSysLogPage(page,sysLog));
    }
}
