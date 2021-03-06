package com.zhangxin.tmall.dao;

import com.zhangxin.tmall.pojo.Category;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CategoryDao {
    //根据categoryId查询出category（首页点击分类列表时用到）(四表关联category/product/fiveimage/review)
    public Category getCategoryById(int id);
    //查询所有种类
    public List<Category> getAllCategoryForAdmin();
    //增加一个category
    public void addCategoryForAdmin(@Param("name") String name,@Param("imageUrl") String imageUrl);
    //更新一个category
    public void updateCategoryForAdmin(@Param("id") int id,@Param("name") String name);
    //删除一个category
    public void deleteCategoryForAdmin(int id);
    //查询此条记录在数据库中是第几条（删除时start跳转需要用到）
    public int getCategoryLocationById(int id);
    ////根据id查询出category
    public Category getCategoryByIdForAdmin(int id);
}
