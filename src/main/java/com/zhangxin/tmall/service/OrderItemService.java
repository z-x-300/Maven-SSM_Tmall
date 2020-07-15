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
    //更新orderItem数量
    public void updateNumberById(int id,int number);
    //根据id获取orderItem
    public OrderItem getOrderItemById(int id);
    //删除orderItem
    public void deleteOrderItemById(int id);
}
