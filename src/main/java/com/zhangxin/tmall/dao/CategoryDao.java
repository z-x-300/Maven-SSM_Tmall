package com.zhangxin.tmall.dao;

import com.zhangxin.tmall.pojo.Category;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CategoryDao {
    //查询所有种类
    public List<Category> getAllCategoryForAdmin();
}
