package com.xianyu.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xianyu.domain.ProdTag;
import com.xianyu.model.Result;
import com.xianyu.service.ProdTagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @BelongsProject: powershop
 * @BelongsPackage: com.xianyu.controller
 * @Author: xianyu
 * @CreateTime: 2023-06-25  16:21
 * @Description: ProdTagController控制层
 * @Version: 1.0
 */
@RestController
@RequestMapping("/prod/prodTag")
@Api(tags = "分组管理的分页实现")
public class ProdTagController {
    @Autowired
    private ProdTagService prodTagService;

    @GetMapping("/page")
    @ApiOperation("分页查询标签列表")
    public Result<Page<ProdTag>> loadProdTagWithPage(Page<ProdTag> page,ProdTag prodTag){
        return Result.success(prodTagService.loadProdTagWithPage(page,prodTag));
    }

    @GetMapping("/listTagList")
    @ApiOperation("加载所有的下拉标签")
    public Result<List<ProdTag>> loadAllProdTag(){
        return Result.success(prodTagService.loadAllProdTag());
    }

    @PostMapping
    @ApiOperation("添加标签列表")
    public Result<String> addProdTag(@RequestBody ProdTag prodTag){
        return prodTagService.addProdTag(prodTag)?Result.success("添加成功"): Result.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(), "添加失败");
    }

    @GetMapping("/info/{id}")
    @ApiOperation("查询标签详情")
    public Result<ProdTag> selectProdTagById(@PathVariable Long id){
        return Result.success( prodTagService.selectProdTagById(id));
    }
    @PutMapping
    @ApiOperation("更新标签列表")
    public Result<String> updateProdTag(@RequestBody ProdTag prodTag){
        return prodTagService.saveOrUpdate(prodTag)?Result.success("添加成功"): Result.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(), "添加失败");
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除标签列表")
    public Result<String> deleteProdTag(@PathVariable Long id){
        return prodTagService.removeById(id)? Result.success("删除成功"): Result.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(), "删除失败");
    }
}
