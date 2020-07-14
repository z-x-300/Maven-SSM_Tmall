package com.zhangxin.tmall.service;

import com.zhangxin.tmall.pojo.OrderItem;

import java.util.List;

public interface OrderItemService {

    //根据orderId获取orderitem
    public List<OrderItem> getOrderItemByOrderId(int orderId);
    //根据用户名获取orderitem
    public List<OrderItem> getOrderItemByUserId(int userId);
    //根据productId 和userId 查询orderItem
    public OrderItem getOrderItemByProductIdAndUserId(int productId,int userId);
    //添加orderItem
    public void addOrderItem(OrderItem orderItem);
    //更新orderItem
    public void updateOrderItem(OrderItem orderItem);
}
