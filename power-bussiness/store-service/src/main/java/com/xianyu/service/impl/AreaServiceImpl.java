package com.xianyu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xianyu.domain.Area;
import com.xianyu.mapper.AreaMapper;
import com.xianyu.service.AreaService;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Objects;

/**
 * @BelongsProject: powershop
 * @BelongsPackage: com.xianyu.service.impl
 * @Author: xianyu
 * @CreateTime: 2023-06-26  20:06
 * @Description: AreaService实现类
 * @Version: 1.0
 */
@Service
public class AreaServiceImpl extends ServiceImpl<AreaMapper, Area> implements AreaService {

    @Override
    public List<Area> loadAreaList(Long pid) {
        return pid==0L?list(new LambdaQueryWrapper<Area>()
                .eq(Area::getParentId,pid)
                .eq(Area::getLevel,1)):list(new LambdaQueryWrapper<Area>()
                .eq(Area::getParentId,pid));
    }
}
