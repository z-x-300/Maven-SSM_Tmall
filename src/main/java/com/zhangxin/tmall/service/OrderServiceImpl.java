package com.zhangxin.tmall.service;

import com.zhangxin.tmall.dao.OrderDao;
import com.zhangxin.tmall.dao.OrderItemDao;
import com.zhangxin.tmall.pojo.Order;
import com.zhangxin.tmall.pojo.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private OrderItemDao orderItemDao;

    //显示所有订单
    public List<Order> getAllOrderByAdmin(){
      List<Order> orderList =orderDao.getAllOrderByAdmin();
        return orderList;
    }

    //更新状态和发货日期
    @Transactional
    public void updateStatusAndDeliverDateById(int id, String status, Date deliverDate){
        orderDao.updateStatusById(id,status);
        orderDao.updateDeliverDateById(id,deliverDate);
    }

    //添加一个订单
    @Transactional
    public void addOrder(Order order,Integer orderItemId){
        orderDao.addOrder(order);
        OrderItem orderItem = new OrderItem();
        orderItem.setId(orderItemId);
        orderItem.setOrderId(order.getId());
        orderItemDao.updateOrderItem(orderItem);
    }
    //根据orderId查询order
    public Order getOrderById(int id){
        Order order =orderDao.getOrderById(id);
        return order;
    }

    //更新状态和paydate
    public void updateStatusAndPayDateById(int id,String status,Date payDate){
        orderDao.updateStatusById(id,status);
        orderDao.updatePayDateById(id,payDate);
    }
    //根据用户id获取所有订单
    public List<Order> getALLOrderByUserId(int userId){
        List<Order> orderList =orderDao.getALLOrderByUserId(userId);
        return orderList;
    }
    //根据id修改确认收货日期
    //更新状态和confirmDate
    public void updateStatusAndConfirmDate(int id,String status,Date confirmDate){
        orderDao.updateStatusById(id,status);
        orderDao.updateConfirmDateById(id,confirmDate);
    }
    //根据id修改状态
    public void updateStatusById(int id,String status){
        orderDao.updateStatusById(id,status);
    }
    //增加一个order并修改多个订单项的orderId(购物车)
    @Transactional
    public void addOrderAndUpdateOrderItems(Order order, Integer[] orderItemIds) {
        orderDao.addOrder(order);
        for(int i = 0;i < orderItemIds.length;i++) {
            OrderItem orderItem = new OrderItem();
            orderItem.setId(orderItemIds[i]);
            orderItem.setOrderId(order.getId());
            orderItemDao.updateOrderItem(orderItem);
        }
    }

}
