package com.xianyu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xianyu.domain.Notice;

public interface NoticeService extends IService<Notice> {
    Page<Notice> loadNoticeWithPage(Page<Notice> page,Notice notice);

    Boolean addNotice(Notice notice);
}
