package com.zhangxin.tmall.dao;

import com.zhangxin.tmall.pojo.OrderItem;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemDao {

     //根据orderId获取orderitem
    public List<OrderItem> getOrderItemByOrderId(int orderId);
    //根据用户名获取orderitem
    public List<OrderItem> getOrderItemByUserId(int userId);
    //根据productId 和userId 查询orderItem
    public OrderItem getOrderItemByProductIdAndUserId(@Param("productId") int productId,@Param("userId") int userId);
    //添加orderItem
    public void addOrderItem(OrderItem orderItem);
    //更新orderItem
    public void updateOrderItem(OrderItem orderItem);

}
