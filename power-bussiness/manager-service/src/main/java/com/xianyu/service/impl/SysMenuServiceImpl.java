package com.xianyu.service.impl;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xianyu.constant.SysMenuConstant;
import com.xianyu.domain.SysMenu;
import com.xianyu.mapper.SysMenuMapper;
import com.xianyu.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @BelongsProject: powershop
 * @BelongsPackage: com.xianyu.service.impl
 * @Author: xianyu
 * @CreateTime: 2023-06-20  10:53
 * @Description: 查询权限信息列表并存入Redis缓存
 * @Version: 1.0
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {
    @Autowired
    private SysMenuMapper sysMenuMapper;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public List<SysMenu> getSysMenusByUserId(Long userId) {
        // 【查询Reids】
        String menuJsonStr = stringRedisTemplate.opsForValue().get(SysMenuConstant.SYS_MENU_PREFIX + userId);
        if (StringUtils.hasText(menuJsonStr)) {
            // 【缓存中存在】
            List<SysMenu> sysMenus= JSON.parseArray(menuJsonStr, SysMenu.class);
            return sysMenus;
        }else {
            // 【缓存中不存在】
            List<SysMenu> sysMenus = sysMenuMapper.selectMenuByUserId(userId);
            List<SysMenu> treeMenu=loadTreeMenuRecursion(sysMenus,0L);
            // 【Duration.ofDays()获取不间断的天数对应时间】
            stringRedisTemplate.opsForValue().set(SysMenuConstant.SYS_MENU_PREFIX + userId, JSONUtil.toJsonStr(treeMenu), Duration.ofDays(Calendar.DAY_OF_WEEK));
            return treeMenu;
        }
    }

    @Override
    public SysMenu selectSysMenu(Long id) {
        return getById(id);
    }

    // 【构建菜单(主菜单、子菜单)关系--指定层次结构】
    // private List<SysMenu> buildTreeMenu(List<SysMenu> sysMenus, long pid) {
    //     // 【获取菜单的根元素(pid=0)】
    //     List<SysMenu> roots=sysMenus.stream()
    //             .filter(sysMenu -> sysMenu.getParentId().equals(pid))
    //             .collect(Collectors.toList());
    //     roots.forEach(menu->{
    //         ArrayList<SysMenu> children = new ArrayList<>();
    //         sysMenus.forEach(r->{
    //             // 【将关联在对应根节点下的子菜单加入子菜单集合】
    //             if (r.getParentId().equals(menu.getMenuId())) {
    //                 children.add(r);
    //             }
    //         });
    //         menu.setList(children);
    //     });
    //     return roots;
    // }

    // 【方式二:使用递归】
    private List<SysMenu> loadTreeMenuRecursion(List<SysMenu> sysMenus, long pid){
        // 【获取每类菜单的头节点】
        List<SysMenu> roots = sysMenus.stream().filter(sysMenu ->
                sysMenu.getParentId().equals(pid)
        ).collect(Collectors.toList());
        // 【找出每个头节点对应的子节点】
        for (SysMenu root : roots) {
            root.setList(loadTreeMenuRecursion(sysMenus,root.getMenuId()));
        }
        return roots;
    }
}
