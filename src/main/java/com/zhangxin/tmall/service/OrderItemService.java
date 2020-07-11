package com.zhangxin.tmall.service;

import com.zhangxin.tmall.pojo.OrderItem;

import java.util.List;

public interface OrderItemService {

    //根据orderId获取orderitem
    public List<OrderItem> getOrderItemByOrderId(int orderId);
    //根据用户名获取orderitem
    public List<OrderItem> getOrderItemByUserId(int userId);
}