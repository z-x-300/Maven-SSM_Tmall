package com.zhangxin.tmall.service;

import com.zhangxin.tmall.dao.CategoryDao;
import com.zhangxin.tmall.pojo.Category;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryDao categoryDao;
    //根据id查询category并映射products
    public Category getCategoryById(int id) {
        Category category = categoryDao.getCategoryById(id);
        return category;
    }
    //查询所有种类
    public List<Category> getAllCategoryForAdmin(){
       List<Category> categoryList =categoryDao.getAllCategoryForAdmin();
       return categoryList;
    }

    //增加一个category
    public void addCategoryForAdmin(@Param("name")String name, @Param("imageUrl") String imageUrl){
        categoryDao.addCategoryForAdmin(name,imageUrl);
    }

    //更新一个category
    public void updateCategoryForAdmin(@Param("id") int id,@Param("name") String name){
        categoryDao.updateCategoryForAdmin(id,name);
    }

    //删除一个category
    public void deleteCategoryForAdmin(int id){
        categoryDao.deleteCategoryForAdmin(id);
    }

    //查询此条记录在数据库中是第几条（删除时start跳转需要用到）
    public int getCategoryLocationById(int id){
        return categoryDao.getCategoryLocationById(id);
    }

    ////根据id查询出category
    public Category getCategoryByIdForAdmin(int id){
        Category category =categoryDao.getCategoryByIdForAdmin(id);
        return category;
    }
}
