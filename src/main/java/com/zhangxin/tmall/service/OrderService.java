package com.zhangxin.tmall.service;

import com.zhangxin.tmall.pojo.Order;

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
    //根据用户id获取所有订单
    public List<Order> getALLOrderByUserId(int userId);
    //更新状态和confirmDate
    public void updateStatusAndConfirmDate(int id,String status,Date confirmDate);
    //根据id修改状态
    public void updateStatusById(int id,String status);
    //增加一个order并修改多个订单项的orderId(购物车)
    public void addOrderAndUpdateOrderItems(Order order,Integer[] orderItemIds);

}
