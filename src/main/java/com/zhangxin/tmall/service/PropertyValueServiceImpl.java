package com.zhangxin.tmall.service;

import com.zhangxin.tmall.dao.PropertyValueDao;
import com.zhangxin.tmall.pojo.PropertyValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropertyValueServiceImpl implements PropertyValueService {

    @Autowired
    private PropertyValueDao propertyValueDao;

    //根据商品Id获取所有属性值
    public List<PropertyValue> getAllPropertyValueByProductIdForAdmin(int productId) {
        List<PropertyValue> propertyValueList = propertyValueDao.getAllPropertyValueByProductIdForAdmin(productId);
        return propertyValueList;
    }

    //根据productId和propertyId获取属性值
    public PropertyValue getPropertyValueByProductIdAndPropertyIdForAdmin(int productId, int propertyId) {
        PropertyValue propertyValue = propertyValueDao.getPropertyValueByProductIdAndPropertyIdForAdmin(productId, propertyId);
        return propertyValue;
    }

    //添加一个属性值
    public void addPropertyValueForAdmin(int productId, int propertyId, String value) {
        propertyValueDao.addPropertyValueForAdmin(productId, propertyId, value);
    }

    //修改属性值
    public void updatePropertyValueForAdmin(int productId, int propertyId, String value) {
        propertyValueDao.updatePropertyValueForAdmin(productId, propertyId, value);
    }

    //根据商品id获取属性值
    public List<PropertyValue> getPropertyValueByProductId(int productId) {
        List<PropertyValue> propertyValueList = propertyValueDao.getPropertyValueByProductId(productId);
        return propertyValueList;
    }

}
