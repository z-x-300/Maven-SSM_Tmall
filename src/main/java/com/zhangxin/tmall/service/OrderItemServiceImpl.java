package com.zhangxin.tmall.service;

import com.zhangxin.tmall.dao.OrderItemDao;
import com.zhangxin.tmall.pojo.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemServiceImpl implements OrderItemService{

    @Autowired
    private OrderItemDao orderItemDao;

    //根据orderId获取orderitem
    public List<OrderItem> getOrderItemByOrderId(int orderId){
        List<OrderItem> orderItemList =orderItemDao.getOrderItemByOrderId(orderId);
        return orderItemList;
    }

    //根据用户名获取orderitem
    public List<OrderItem> getOrderItemByUserId(int userId){
       List<OrderItem> orderItemList =orderItemDao.getOrderItemByUserId(userId);
        return orderItemList;
    }

    //根据productId 和userId 查询orderItem
    public OrderItem getOrderItemByProductIdAndUserId(int productId,int userId){
        OrderItem orderItem=orderItemDao.getOrderItemByProductIdAndUserId(productId,userId);
        return orderItem;
    }
    //添加orderItem
    public void addOrderItem(OrderItem orderItem){
        orderItemDao.addOrderItem(orderItem);
    }
    //更新orderItem
    public void updateOrderItem(OrderItem orderItem){
        orderItemDao.updateOrderItem(orderItem);
    }
}
