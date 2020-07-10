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
}
