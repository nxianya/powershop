package com.xianyu.controller;

import com.xianyu.domain.Area;
import com.xianyu.model.Result;
import com.xianyu.service.AreaService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @BelongsProject: powershop
 * @BelongsPackage: com.xianyu.controller
 * @Author: xianyu
 * @CreateTime: 2023-06-26  20:08
 * @Description: AreaController控制层
 * @Version: 1.0
 */
@RestController
@RequestMapping("/admin/area")
public class AreaController {
    @Autowired
    private AreaService areaService;
    @GetMapping("/listByPid")
    @ApiOperation("获取地区列表")
    public Result<List<Area>> loadAreaList(Long pid){
        return Result.success(areaService.loadAreaList(pid));
    }


}
