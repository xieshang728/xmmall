package com.xmall.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.xmall.common.RestResponse;
import com.xmall.dao.CategoryMapper;
import com.xmall.entity.Category;
import com.xmall.service.ICategoryService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * @author xies
 * @date 2018/1/23
 */
@Service
public class CategoryServiceImpl implements ICategoryService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public RestResponse<String> addCategory(String categoryName, Integer parentId) {
        try {
            if (parentId == null || StringUtils.isBlank(categoryName)) {
                return RestResponse.error("添加品类参数错误");
            }
            Category category = new Category();
            category.setName(categoryName);
            category.setParentId(parentId);
            category.setStatus(true);
            int resultCount = categoryMapper.insertSelective(category);
            if (resultCount == 1) {
                return RestResponse.success("添加品类成功");
            }
        } catch (Exception e) {
            logger.error("addCategory exception: " + e.toString());
        }
        return RestResponse.error("添加品类失败");
    }

    @Override
    public RestResponse<String> setCategoryName(String categoryName, Integer categoryId) {
        try {
            if (categoryId == null || StringUtils.isBlank(categoryName)) {
                return RestResponse.error("参数错误");
            }
            Category category = new Category();
            category.setName(categoryName);
            category.setId(categoryId);
            int resultCount = categoryMapper.updateByPrimaryKeySelective(category);
            if (resultCount == 1) {
                return RestResponse.success("修改产品类成功");
            }
        } catch (Exception e) {
            logger.error("setCategoryName exception: " + e.toString());
        }
        return RestResponse.error("添加产品类失败");
    }


    @Override
    public RestResponse<List<Category>> getChildrenParallelCategory(Integer categoryId) {
        try {
            if (categoryId == null) {
                return RestResponse.error("参数错误");
            }
            List<Category> categoryList = categoryMapper.selectCategoryChildrenByParentId(categoryId);
            if (categoryList != null) {
                return RestResponse.success(categoryList);
            }
        } catch (Exception e) {
            logger.error("getChildrenParallelCategory exception: " + e.toString());
        }
        return RestResponse.error("category获取失败");
    }

    @Override
    public RestResponse<List<Integer>> selectCategoryAndChildrenById(Integer categoryId) {
        try {
            if (categoryId == null) {
                return RestResponse.error("参数错误");
            }
            Set<Category> categorySet = Sets.newHashSet();
            findChildCategory(categorySet, categoryId);
            List<Integer> categoryIdList = Lists.newArrayList();
            if (categorySet != null) {
                for (Category category : categorySet) {
                    categoryIdList.add(category.getId());
                }
                return RestResponse.success(categoryIdList);
            }

        } catch (Exception e) {
            logger.error("selectCategoryAndChildrenById exception: " + e.toString());
        }
        return RestResponse.error("产品子类获取失败");
    }

    private Set<Category> findChildCategory(Set<Category> categorySet, Integer categoryId) {
        Category category = categoryMapper.selectByPrimaryKey(categoryId);
        if (category != null) {
            categorySet.add(category);
        }
        List<Category> categoryList = categoryMapper.selectCategoryChildrenByParentId(categoryId);
        for (Category item : categoryList) {
            findChildCategory(categorySet, item.getId());
        }
        return categorySet;
    }
}
