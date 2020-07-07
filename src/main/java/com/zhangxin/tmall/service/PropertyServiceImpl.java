package com.zhangxin.tmall.service;


import com.zhangxin.tmall.dao.PropertyDao;
import com.zhangxin.tmall.pojo.Property;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropertyServiceImpl implements PropertyService{

    @Autowired
    private PropertyDao propertyDao;

    //根据category的id查询出对应的属性
    public List<Property> getPropertyByCategoryId(int categoryId){
        List<Property> propertyList =propertyDao.getPropertyByCategoryId(categoryId);
        return propertyList;
    }

    //添加属性
    public void addPropertyForAdmin(@Param("name") String name, @Param("categoryId") Integer categoryId){
        propertyDao.addPropertyForAdmin(name,categoryId);
    }

    //根据id修改属性
    public void updatePropertyForAdmin(int propertyId,String propertyName){
        propertyDao.updatePropertyForAdmin(propertyId,propertyName);
    }


    public void deletePropertyForAdmin(int id) {
        propertyDao.deletePropertyForAdmin(id);
    }

    public int getPropertyLocation(int id) {
      return propertyDao.getPropertyLocation(id);
    }
}
