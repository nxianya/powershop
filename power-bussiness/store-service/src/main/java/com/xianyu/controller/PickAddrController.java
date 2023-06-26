package com.xianyu.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xianyu.domain.PickAddr;
import com.xianyu.model.Result;
import com.xianyu.service.PickAddrService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * @BelongsProject: powershop
 * @BelongsPackage: com.xianyu.controller
 * @Author: xianyu
 * @CreateTime: 2023-06-26  19:46
 * @Description: PickAddrController控制层
 * @Version: 1.0
 */
@RestController
@RequestMapping("/shop/pickAddr")
public class PickAddrController {
    @Autowired
    private PickAddrService pickAddrService;
    @GetMapping("/page")
    @ApiOperation("查询分页自提点信息")
    public Result<Page<PickAddr>> loadPickAddrWithPage(Page<PickAddr> page,PickAddr pickAddr){
        return Result.success(pickAddrService.loadPickAddrWithPage(page,pickAddr));
    }

    @PostMapping
    @ApiOperation("添加自提点")
    public Result<String> addPickAddr(@RequestBody PickAddr pickAddr){
        return pickAddrService.save(pickAddr)? Result.success("添加成功"): Result.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(), "添加失败");
    }
    @GetMapping("/info/{id}")
    @ApiOperation("查询自提点详情")
    public Result<PickAddr> selectPickAddr(@PathVariable Long id){
        return Result.success(pickAddrService.getById(id));
    }

    @PutMapping
    @ApiOperation("修改自提点信息")
    public Result<String> updatePickAddrInfo(@RequestBody PickAddr pickAddr){
        return pickAddrService.updateById(pickAddr)? Result.success("修改成功"): Result.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(), "修改失败");
    }

    @DeleteMapping
    @ApiOperation("删除自提点")
    public Result<String> deletePickAddr(@RequestBody Long id){
        System.out.println(id);
        return pickAddrService.removeById(id)? Result.success("删除成功"): Result.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(), "删除失败");
    }
}
