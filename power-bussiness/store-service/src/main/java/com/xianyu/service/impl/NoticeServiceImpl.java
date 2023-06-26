package com.xianyu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xianyu.domain.Notice;
import com.xianyu.mapper.NoticeMapper;
import com.xianyu.service.NoticeService;
import com.xianyu.utils.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * @BelongsProject: powershop
 * @BelongsPackage: com.xianyu.service.impl
 * @Author: xianyu
 * @CreateTime: 2023-06-26  15:27
 * @Description: NoticeService实现类
 * @Version: 1.0
 */
@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements NoticeService {
    @Autowired
    private NoticeMapper noticeMapper;
    @Override
    public Page<Notice> loadNoticeWithPage(Page<Notice> page, Notice notice) {
        return noticeMapper.selectPage(page,new LambdaQueryWrapper<Notice>()
                .like(StringUtils.hasText(notice.getTitle()),Notice::getTitle,notice.getTitle())
                .eq(!ObjectUtils.isEmpty(notice.getStatus()),Notice::getStatus,notice.getStatus())
                .eq(!ObjectUtils.isEmpty(notice.getIsTop()),Notice::getIsTop,notice.getIsTop()));
    }

    @Override
    public Boolean addNotice(Notice notice) {
        notice.setId(null);
        notice.setCreateTime(new Date());
        notice.setShopId(AuthUtil.getShopId());
        notice.setUpdateTime(new Date());
        return save(notice);
    }
}
