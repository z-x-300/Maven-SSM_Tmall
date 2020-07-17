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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

     //显示订单
     @RequestMapping("/order/showOrders.do")
     public ModelAndView showOrder(HttpServletRequest request){
         HttpSession session = request.getSession();
         User user = (User) session.getAttribute("user");
         //获取user所对应的订单集合和订单集合所对应的订单项集合
         List<Order> orderList = orderService.getALLOrderByUserId(user.getId());
         //设置二维集合保存orderList中对应的orderItemList
         ArrayList<ArrayList<OrderItem>> orderItems = new ArrayList<ArrayList<OrderItem>>();
         for(int i = 0;i < orderList.size();i++) {
             ArrayList<OrderItem> orderItemList = (ArrayList<OrderItem>) orderItemService.getOrderItemByOrderId(orderList.get(i).getId());
             orderItems.add(orderItemList);
         }
         //获取订单项中所对应的商品集合(已经关联了fiveimage)
         ArrayList<ArrayList<Product>> products = new ArrayList<ArrayList<Product>>();
         for(int i = 0;i < orderItems.size();i++) {
             ArrayList<Product> productList = new ArrayList<Product>();
             for(int j = 0;j < orderItems.get(i).size();j++) {
                 int productId = orderItems.get(i).get(j).getProductId();
                 Product product = productService.getProductById(productId);
                 productList.add(product);
             }
             products.add(productList);
         }
         ModelAndView modelAndView = new ModelAndView();
         modelAndView.addObject("orderList",orderList);
         modelAndView.addObject("orderItems",orderItems);
         modelAndView.addObject("products",products);
         modelAndView.setViewName("anotherPage/myOrder");
         return modelAndView;
     }

    //订单中点击付款
    @RequestMapping("/order/myOrderPayment.do")
    public ModelAndView myOrderPayment(Integer orderId) {
        float totalMoney = 0;
        //根据orderId查询出orderItem的数量集合
        List<OrderItem> orderItems = orderItemService.getOrderItemByOrderId(orderId);
        //计算订单项中所有的商品的总价格
        for(int i = 0;i < orderItems.size();i++) {
            int number = orderItems.get(i).getNumber();
            int productId = orderItems.get(i).getProductId();
            Product product = productService.getProductById(productId);
            float productMoney = product.getPromotePrice();
            totalMoney = totalMoney + productMoney * number;
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/jsp/anotherPage/payment.jsp?totalMoney=" + totalMoney + "&orderId=" + orderId);
        return modelAndView;
    }


    //订单中点击催卖家发货（已付款）
    @RequestMapping("/order/myOrderAlreadyPayment.do")
    public ModelAndView myOrderAlreadyPay(Integer orderId) {
        //修改订单中的status和发货日期
        Date deliverDate = new Date();
        String status = "待收货";
        orderService.updateStatusAndDeliverDateById(orderId,status,deliverDate);
        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:/order/showOrders.do");
        return mav;
    }

    //订单中点击确认收货
    @RequestMapping("/order/myOrderConfirmPay.do")
    public ModelAndView myOrderConfirmPay(Integer orderId) {
        //根据orderId获取order
        Order order = orderService.getOrderById(orderId);
        //根据orderId获取对应的orderItem集合
        List<OrderItem> orderItems = orderItemService.getOrderItemByOrderId(orderId);
        //根据orderItemId获取productId集合
        List<Product> products = new ArrayList<Product>();
        for(int i = 0;i < orderItems.size();i++) {
            int productId = orderItems.get(i).getProductId();
            Product product = productService.getProductById(productId);
            products.add(product);
        }
        //计算价格总数
        float totalMoney = 0;
        for(int i = 0;i < orderItems.size();i++) {
            int number = orderItems.get(i).getNumber();
            float money = products.get(i).getPromotePrice();
            totalMoney = totalMoney + number * money;
        }
        //设置成交时间和订单状态
        Date confirmDate = new Date();
        String status = "待评价";
        orderService.updateStatusAndConfirmDate(orderId,status,confirmDate);
        //保存order和orderItems和Products和totalMoney和confirmDate
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("order",order);
        modelAndView.addObject("orderItems",orderItems);
        modelAndView.addObject("products",products);
        modelAndView.addObject("totalMoney",totalMoney);
        modelAndView.addObject("confirmDate",confirmDate);
        modelAndView.setViewName("/anotherPage/confirmReceipt");
        return modelAndView;
    }

    //订单中点击删除
    @RequestMapping("/order/deleteOrder.do")
    @ResponseBody
    public String deleteOrder(Integer orderId) {
        //修改order中的状态为已删除
        String status = "已删除";
        orderService.updateStatusById(orderId,status);
        return "deleteSuccessful";
    }

    //创建订单(购物车中的)
    @RequestMapping("/order/addOrderForShoppingCart.do")
    public synchronized ModelAndView addOrderForShoppingCart(Integer[] orderItemId,String totalMoney,Order order,HttpServletRequest request) {
        Integer[] orderItemIds = orderItemId;
        Date createDate = new Date();
        //使用时间戳加上一个随机数作为订单编码
        long orderCode = createDate.getTime() + (int) (Math.random()*10);
        //设置状态为待付款
        String status = "待付款";
        //设置订单的userId
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        order.setUserId(user.getId());
        order.setCreateDate(createDate);
        order.setOrderCode(String.valueOf(orderCode));
        order.setStatus(status);
        orderService.addOrderAndUpdateOrderItems(order,orderItemIds);
        //循环根据多个orderItem修改商品的库存和销量
        for(int i = 0;i < orderItemIds.length;i++) {
            OrderItem orderItem = orderItemService.getOrderItemById(orderItemIds[i]);
            productService.updateStockAndSaleCountById(orderItem.getProductId(),orderItem.getNumber());
            //判断如果库存小于或等于0就入货到100
            Product product = productService.getProductById(orderItem.getProductId());
            if(product.getStock() <= 0) {
                productService.updateStockById(orderItem.getProductId());
            }
        }
        //更新购物车数量
        session.removeAttribute("orderItemNumber");
        List<OrderItem> orderItem2 = orderItemService.getOrderItemByUserId(user.getId());
        int orderItemNumber = orderItem2.size();
        session.setAttribute("orderItemNumber",orderItemNumber);
        ModelAndView mav = new ModelAndView("redirect:/jsp/anotherPage/payment.jsp?totalMoney=" + totalMoney + "&orderId=" + order.getId());
        return mav;
    }

    //订单中点击评价
    @RequestMapping("/order/myOrderReview.do")
    public ModelAndView myOrderReview(Integer productId,Integer orderId) {
        //根据productId获取product
        Product product = productService.getProductById(productId);
        //根据orderId获取order
        Order order = orderService.getOrderById(orderId);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("order",order);
        modelAndView.addObject("product",product);
        modelAndView.setViewName("/anotherPage/review");
        return modelAndView;
    }
}
