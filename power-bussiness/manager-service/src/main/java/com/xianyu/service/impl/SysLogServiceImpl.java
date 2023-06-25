package com.xianyu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xianyu.domain.SysLog;
import com.xianyu.mapper.SysLogMapper;
import com.xianyu.service.SysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @BelongsProject: powershop
 * @BelongsPackage: com.xianyu.service.impl
 * @Author: xianyu
 * @CreateTime: 2023-06-23  10:28
 * @Description: TODO
 * @Version: 1.0
 */
@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements SysLogService {
    @Autowired
    private SysLogMapper sysLogMapper;
    @Override
    public Page<SysLog> loadSysLogPage(Page<SysLog> page, SysLog sysLog) {
        return sysLogMapper.selectPage(page,new LambdaQueryWrapper<SysLog>().orderByDesc(SysLog::getCreateDate));
    }
}
