package com.xianyu.controller;

import com.xianyu.domain.Category;
import com.xianyu.model.Result;
import com.xianyu.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @BelongsProject: powershop
 * @BelongsPackage: com.xianyu.controller
 * @Author: xianyu
 * @CreateTime: 2023-06-24  15:07
 * @Description: 商品类别控制层
 * @Version: 1.0
 */
@RestController
@RequestMapping("/prod/category")
@Api(tags = "商品类别接口")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @GetMapping({"/table","/listCategory"})
    @ApiOperation("加载商品所有的类别")
    public Result<List<Category>> loadAllCategory(){
        return Result.success(categoryService.list());
    }

    @PostMapping
    @ApiOperation("新增商品类别")

    public Result<String> addCategory(@RequestBody Category category){
            categoryService.addCategory(category);
            return Result.success("添加成功");
    }
    @GetMapping("/info/{categoryId}")
    @ApiOperation("查询商品类别信息")
    public Result<Category> categoryInfoById(@PathVariable("categoryId") Long categoryId){
        return Result.success(categoryService.categoryInfoById(categoryId));
    }

    @PutMapping
    @ApiOperation("修改商品类别信息")
    public Result<String> updateCategory(@RequestBody Category category){
        categoryService.updateCategory(category);
        return Result.success("修改成功~");
    }

    @DeleteMapping("/{categoryId}")
    @ApiOperation("删除商品类别")
    public Result<String> deleteCategory(@PathVariable("categoryId") Long categoryId){
        categoryService.deleteCategory(categoryId);
        return Result.success("删除成功~");
    }
}
