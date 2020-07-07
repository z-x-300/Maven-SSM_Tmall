package com.zhangxin.tmall.dao;

import com.zhangxin.tmall.pojo.Property;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropertyDao {
    //根据category的id查询出对应的属性
    public List<Property> getPropertyByCategoryId(int categoryId);
    //添加属性
    public void addPropertyForAdmin(@Param("name") String name,@Param("categoryId") Integer categoryId);
    //根据id修改属性
    public void updatePropertyForAdmin(@Param("propertyId") Integer propertyId,@Param("propertyName") String propertyName);
    //根据id删除属性
    public void deletePropertyForAdmin(int id);
    //查询此条记录在数据库中是第几条（删除时start跳转需要用到）
    public int getPropertyLocation(int id);
}
