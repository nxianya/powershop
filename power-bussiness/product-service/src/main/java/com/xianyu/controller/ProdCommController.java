package com.xianyu.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xianyu.domain.ProdComm;
import com.xianyu.model.Result;
import com.xianyu.service.ProdCommService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * @BelongsProject: powershop
 * @BelongsPackage: com.xianyu.controller
 * @Author: xianyu
 * @CreateTime: 2023-06-25  22:43
 * @Description: ProdCommController控制层
 * @Version: 1.0
 */
@RestController
@RequestMapping("/prod/prodComm")
public class ProdCommController {
    @Autowired
    private ProdCommService prodCommService;
    @GetMapping("/page")
    @ApiOperation("分页评论查询")
    public Result<Page<ProdComm>> loadProdCommPage(Page<ProdComm> page,ProdComm prodComm){
        return Result.success(prodCommService.loadProdCommPage(page,prodComm));
    }

    @GetMapping("/{id}")
    @ApiOperation("查看评论详情")
    public Result<ProdComm> selectProdCommInfo(@PathVariable("id") Long prodCommId){
        return Result.success(prodCommService.getById(prodCommId));
    }

    @PutMapping
    @ApiOperation("审核评论信息")
    public Result<String> updateProdCommInfo(@RequestBody ProdComm prodComm){
        return prodCommService.updateProdCommInfo(prodComm)? Result.success("编辑成功"):Result.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(), "编辑失败");
    }
}
