package com.zhangxin.tmall.dao;

import com.zhangxin.tmall.pojo.Property;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropertyDao {
    //根据category的id查询出对应的属性
    public List<Property> getPropertyByCategoryId(int categoryId);
}
