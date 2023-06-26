package com.xianyu.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xianyu.domain.Notice;
import com.xianyu.model.Result;
import com.xianyu.service.NoticeService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @BelongsProject: powershop
 * @BelongsPackage: com.xianyu.controller
 * @Author: xianyu
 * @CreateTime: 2023-06-26  15:34
 * @Description: NoticeController控制层
 * @Version: 1.0
 */
@RestController
@RequestMapping("/shop/notice")
@Slf4j
public class NoticeController {
    @Autowired
    private NoticeService noticeService;
    @GetMapping("/page")
    @ApiOperation("分页查询公告")
    public Result<Page<Notice>> loadNoticeWithPage(Page<Notice> page,Notice notice){

        return Result.success(noticeService.loadNoticeWithPage(page,notice));
    }

    @GetMapping("/info/{id}")
    @ApiOperation("查看公告详情")
    public Result<Notice> selectNoticeById(@PathVariable("id") Long noticeId){
        return Result.success(noticeService.getOne(new LambdaQueryWrapper<Notice>()
                .eq(Notice::getId, noticeId)));
    }

    @PostMapping
    @ApiOperation("添加公告")
    public Result<String> addNotice(@RequestBody Notice notice){
        return noticeService.addNotice(notice)?Result.success("添加成功"): Result.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(), "添加失败");
    }

    @PutMapping
    @ApiOperation("更新公告详情")
    public Result<String> updateNotice(@RequestBody Notice notice){
        notice.setUpdateTime(new Date());
        return noticeService.updateById(notice)? Result.success("添加成功"): Result.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(), "添加失败");
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除公共")
    public Result<Integer> deleteNotice(@PathVariable("id") Long id){
        return noticeService.removeById(id)?Result.success("删除成功"): Result.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(), "删除失败");
    }
}
