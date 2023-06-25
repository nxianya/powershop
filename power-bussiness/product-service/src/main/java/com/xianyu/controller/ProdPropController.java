package com.xianyu.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xianyu.domain.ProdProp;
import com.xianyu.domain.ProdPropValue;
import com.xianyu.model.Result;
import com.xianyu.service.ProdPropService;
import com.xianyu.service.ProdPropValueService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @BelongsProject: powershop
 * @BelongsPackage: com.xianyu.controller
 * @Author: xianyu
 * @CreateTime: 2023-06-25  10:44
 * @Description: 属性规格控制层
 * @Version: 1.0
 */
@RestController
@RequestMapping("/prod/spec")
@Api(tags = "属性规格接口")
public class ProdPropController {
    @Autowired
    private ProdPropService prodPropService;

    @Autowired
    private ProdPropValueService prodPropValueService;


    @GetMapping("/page")
    @ApiOperation("分页展示属性及属性值")
    public Result<Page<ProdProp>> loadProdPropPage( Page<ProdProp> page,ProdProp prodProp){
        return Result.success(prodPropService.loadProdPropWithPage(page,prodProp));
    }

    @PostMapping
    @ApiOperation("增加属性和属性值")
    public Result<Integer> addProdProp(@RequestBody ProdProp prodProp){
        return Result.success(prodPropService.addProdProp(prodProp));
    }

    @GetMapping("/list")
    @ApiOperation("加载当前店铺的所有属性规格")
    public Result<List<ProdProp>> loadAllProdProp(){
        return Result.success(prodPropService.loadAllProdProp());
    }

    @GetMapping("/listSpecValue/{prodId}")
    @ApiOperation("加载特殊属性对应的属性值的集合")
    public Result<List<ProdPropValue>> loadProdPropValuesByProdId(@PathVariable("prodId") Long prodPropId){
        return Result.success(prodPropValueService.loadProdPropValuesByProdId(prodPropId));
    }
}
