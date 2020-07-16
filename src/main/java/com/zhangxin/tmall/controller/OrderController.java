package com.zhangxin.tmall.controller;

import com.zhangxin.tmall.pojo.Order;
import com.zhangxin.tmall.pojo.OrderItem;
import com.zhangxin.tmall.pojo.Product;
import com.zhangxin.tmall.pojo.User;
import com.zhangxin.tmall.service.OrderItemService;
import com.zhangxin.tmall.service.OrderService;
import com.zhangxin.tmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private ProductService productService;

    @RequestMapping("/order/addOrder.do")
    public synchronized ModelAndView addOrder(Order order, Integer orderItemId, float totalMoney, HttpServletRequest request) {
        Date createDate = new Date();
        //随机生成订单号，创建的时间加一个随机数
        long orderCode = createDate.getTime() + (int) (Math.random() * 10);
        //设置订单状态为待付款
        String status = "待付款";
        //设置订单的userId
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        order.setUserId(user.getId());
        order.setCreateDate(createDate);
        order.setOrderCode(String.valueOf(orderCode));
        order.setStatus(status);
        //添加一个订单
        orderService.addOrder(order, orderItemId);
        //根据orderItemId查询出orderItem
        OrderItem orderItem = orderItemService.getOrderItemById(orderItemId);
        //根据orderItem查询商品再修改商品中的库存和月销量
        productService.updateStockAndSaleCountById(orderItem.getProductId(), orderItem.getNumber());
        //根据orderItem查询商品
        Product product = productService.getProductById(orderItem.getProductId());
        //判断该商品库存是否小于等于0
        if (product.getStock() <= 0) {//商品库存小于等于0，自动添加库存
            productService.updateStockById(orderItem.getProductId());
        }
        session.removeAttribute("orderItemNumber");
        List<OrderItem> orderItem2 = orderItemService.getOrderItemByUserId(user.getId());
        int orderItemNumber = orderItem2.size();
        session.setAttribute("orderItemNumber", orderItemNumber);
        ModelAndView modelAndView = new ModelAndView("redirect:/jsp/anotherPage/payment.jsp?totalMoney=" + totalMoney + "&orderId=" + order.getId());
        return modelAndView;
    }

    //付款
    @RequestMapping("/order/payment.do")
    public ModelAndView payment(float totalMoney, Integer orderId) {
        //根据orderId查询出order
        Order order = orderService.getOrderById(orderId);
        //设置收货时间为createDate的后3天
        Date date = order.getCreateDate();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DAY_OF_MONTH, 3);
        Date date1 = c.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat();
        String date2 = sdf.format(date1);
        //更新order中的状态和付款时间
        String status = "已付款";
        Date payDate = new Date();
        orderService.updateStatusAndPayDateById(orderId, status, payDate);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("order", order);
        modelAndView.addObject("totalMoney", totalMoney);
        modelAndView.addObject("date2", date2);
        modelAndView.setViewName("/anotherPage/successPayment");
        return modelAndView;
    }
}
