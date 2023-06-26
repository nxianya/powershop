package com.xianyu.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xianyu.domain.Prod;
import com.xianyu.dto.ProdDto;
import com.xianyu.model.Result;
import com.xianyu.service.ProdService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * @BelongsProject: powershop
 * @BelongsPackage: com.xianyu.controller
 * @Author: xianyu
 * @CreateTime: 2023-06-25  19:03
 * @Description: ProdController控制层
 * @Version: 1.0
 */
@RestController
@RequestMapping("/prod/prod")
@Slf4j
public class ProdController {
    @Autowired
    private ProdService prodService;

    @GetMapping("/page")
    @ApiOperation("查询产品分页列表接口")
    public Result<Page<Prod>> loadProdPage(Page<Prod> page,Prod prod){
        return Result.success(prodService.loadProdWithPage(page,prod));
    }

    @GetMapping("/info/{id}")
    @ApiOperation("根据Id查询产品信息")
    public Result<ProdDto> selectProdById(@PathVariable("id") Long id){
        // ProdDto prodDto = prodService.selectProdById(id);
        // System.out.println(prodDto);
        return Result.success(prodService.selectProdById(id));
    }

    @PostMapping
    @ApiOperation("添加产品")
    public Result<Integer> addProd(@RequestBody ProdDto prodDto){
        // System.out.println(prod);
        return Result.success(prodService.addProd(prodDto));
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除产品")
    public Result<String> deleteProd(@PathVariable Long id){
        return prodService.deleteProd(id)? Result.success("删除成功"): Result.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(), "删除失败");
    }

    @PutMapping
    public Result<String> updateProd(@RequestBody ProdDto prodDto){
        prodService.updateProd(prodDto);
        return Result.success("修改成功");
    }
}
