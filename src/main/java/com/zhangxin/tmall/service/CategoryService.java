package com.zhangxin.tmall.service;

import com.zhangxin.tmall.dao.CategoryDao;
import com.zhangxin.tmall.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryDao categoryDao;

    //查询所有种类
    public List<Category> getAllCategoryForAdmin(){
       List<Category> categoryList =categoryDao.getAllCategoryForAdmin();
       return categoryList;
    }
}
