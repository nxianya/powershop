package com.xianyu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xianyu.domain.Area;

import java.util.List;

public interface AreaService extends IService<Area> {
    List<Area> loadAreaList(Long pid);
}
