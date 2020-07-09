package com.zhangxin.tmall.service;

import com.zhangxin.tmall.pojo.PropertyValue;

import java.util.List;

public interface PropertyValueService {
    //根据商品Id获取所有属性值
    public List<PropertyValue> getAllPropertyValueByProductIdForAdmin(int productId);
    //根据productId和propertyId获取属性值
    public PropertyValue getPropertyValueByProductIdAndPropertyIdForAdmin( int productId, int propertyId);
    //添加一个属性值
    public void addPropertyValueForAdmin(int productId,int propertyId, String value);
    //修改属性值
    public void updatePropertyValueForAdmin(int productId,int propertyId,String value);
}
