package com.xmall.service;

import com.xmall.common.RestResponse;
import com.xmall.entity.Category;

import java.util.List;

/**
 * @author xies
 * @date 2018/1/23
 */
public interface ICategoryService {
    RestResponse<String> addCategory(String categoryName, Integer parentId);

    RestResponse<String> setCategoryName(String categoryName, Integer categoryId);

    RestResponse<List<Category>> getChildrenParallelCategory(Integer categoryId);

    RestResponse<List<Integer>> selectCategoryAndChildrenById(Integer categoryId);
}
