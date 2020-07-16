package com.zhangxin.tmall.dao;

import com.zhangxin.tmall.pojo.Order;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface OrderDao {
    //显示所有订单
    public List<Order> getAllOrderByAdmin();
    //根据id修改订单状态
    public void updateStatusById(@Param("id") int id, @Param("status") String status);
    //根据id修改订单发货日期
    public void updateDeliverDateById(@Param("id") int id,@Param("deliverDate") Date deliverDate);
    //添加一个订单
    public void addOrder(Order order);
    //根据orderId查询order
    public Order getOrderById(int id);
    //根据id修改payDate
    public void updatePayDateById(@Param("id") int id,@Param("payDate") Date payDate);

}
