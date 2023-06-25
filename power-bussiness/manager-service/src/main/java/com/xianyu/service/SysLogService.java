package com.xianyu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xianyu.domain.SysLog;

public interface SysLogService extends IService<SysLog> {
    Page<SysLog> loadSysLogPage(Page<SysLog> page,SysLog sysLog);
}
