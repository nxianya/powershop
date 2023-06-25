package com.xianyu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xianyu.constant.CategoryConstant;
import com.xianyu.domain.Category;
import com.xianyu.mapper.CategoryMapper;
import com.xianyu.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.List;

/**
 * @BelongsProject: powershop
 * @BelongsPackage: com.xianyu.service.impl
 * @Author: xianyu
 * @CreateTime: 2023-06-24  14:48
 * @Description: CategoryService实现类
 * @Version: 1.0
 */
@Service
@CacheConfig(cacheNames = "com.xianyu.service.impl.CategoryServiceImpl")
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    @Cacheable(key = CategoryConstant.CATEGORY_ALL_KEY)
    public List<Category> list() {
        List<Category> categoryList = categoryMapper.selectList(new LambdaQueryWrapper<Category>().orderByDesc(Category::getSeq));
        return categoryList;
    }

    @Override
    public Category categoryInfoById(Long categoryId) {
        return categoryMapper.selectOne(new LambdaQueryWrapper<Category>().eq(!ObjectUtils.isEmpty(categoryId),Category::getCategoryId,categoryId));
    }

    @Override
    @CacheEvict(key = CategoryConstant.CATEGORY_ALL_KEY,allEntries = true)
    public void updateCategory(Category category) {
        category.setUpdateTime(new Date());
        updateById(category);
    }

    @Override
    @CacheEvict(key = CategoryConstant.CATEGORY_ALL_KEY,allEntries = true)
    public void addCategory(Category category) {
        category.setCreateTime(new Date());
        category.setUpdateTime(new Date());
        category.setPic("http://"+category.getPic());
        save(category);
    }

    @Override
    @CacheEvict(key = CategoryConstant.CATEGORY_ALL_KEY,allEntries = true)
    public void deleteCategory(Long categoryId) {
        categoryMapper.deleteById(categoryId);
    }
}
