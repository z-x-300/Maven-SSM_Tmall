package com.zhangxin.tmall.dao;

import com.zhangxin.tmall.pojo.PropertyValue;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropertyValueDao {
    //根据商品Id获取所有属性值
    public List<PropertyValue> getAllPropertyValueByProductIdForAdmin(int productId);

    //根据productId和propertyId获取属性值
    public PropertyValue getPropertyValueByProductIdAndPropertyIdForAdmin(@Param("productId") int productId, @Param("propertyId") int propertyId);

    //添加一个属性值
    public void addPropertyValueForAdmin(@Param("productId") int productId, @Param("propertyId") int propertyId, @Param("value") String value);

    //修改属性值
    public void updatePropertyValueForAdmin(@Param("productId") int productId, @Param("propertyId") int propertyId, @Param("value") String value);

    //根据商品id获取属性值
    public List<PropertyValue> getPropertyValueByProductId(int productId);
}
