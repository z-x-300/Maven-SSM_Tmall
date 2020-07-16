package com.zhangxin.tmall.service;

import com.zhangxin.tmall.pojo.Order;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

public interface OrderService {
    //显示所有订单
    public List<Order> getAllOrderByAdmin();
    //更新状态和发货日期
    public void updateStatusAndDeliverDateById(int id, String status, Date deliverDate);
    //添加一个订单
    public void addOrder(Order order,Integer orderItemId);
    //根据orderId查询order
    public Order getOrderById(int id);
    //更新状态和paydate
    public void updateStatusAndPayDateById(int id,String status,Date payDate);

}
