package com.zhangxin.tmall.dao;

import com.zhangxin.tmall.pojo.OrderItem;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemDao {

     //根据orderId获取orderitem
    public List<OrderItem> getOrderItemByOrderId(int orderId);
}
