package com.xianyu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xianyu.domain.Category;

import java.util.ArrayList;
import java.util.List;

/**
 * @BelongsProject: powershop
 * @BelongsPackage: com.xianyu.service
 * @Author: xianyu
 * @CreateTime: 2023-06-24  11:46
 * @Description: TODO
 * @Version: 1.0
 */
public interface CategoryService extends IService<Category> {
    List<Category> list();

    Category categoryInfoById(Long categoryId);

    void updateCategory(Category category);

    void addCategory(Category category);

    void deleteCategory(Long categoryId);
}
