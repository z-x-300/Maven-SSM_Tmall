package com.zhangxin.tmall.service;

import com.zhangxin.tmall.dao.OrderDao;
import com.zhangxin.tmall.pojo.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderDao orderDao;

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
}
