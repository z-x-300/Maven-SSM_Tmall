package com.zhangxin.tmall.service;


import com.zhangxin.tmall.dao.PropertyDao;
import com.zhangxin.tmall.pojo.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropertyService {

    @Autowired
    private PropertyDao propertyDao;

    public List<Property> getPropertyByCategoryId(int categoryId){
        List<Property> propertyList =propertyDao.getPropertyByCategoryId(categoryId);
        return propertyList;
    }
}
