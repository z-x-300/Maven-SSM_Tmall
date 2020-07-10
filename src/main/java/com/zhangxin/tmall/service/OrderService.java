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

}
