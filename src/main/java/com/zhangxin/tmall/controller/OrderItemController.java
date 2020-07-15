package com.zhangxin.tmall.controller;

import com.zhangxin.tmall.pojo.OrderItem;
import com.zhangxin.tmall.pojo.Product;
import com.zhangxin.tmall.pojo.User;
import com.zhangxin.tmall.service.OrderItemService;
import com.zhangxin.tmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class OrderItemController {

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private ProductService productService;
    //购买
    @RequestMapping("/orderItem/addOrderItem.do")
    public ModelAndView addToShoppingCart(Integer productId, Integer number, String request_uuid, HttpServletRequest request){
        HttpSession httpSession = request.getSession();
        User user = (User) httpSession.getAttribute("user");
        //session的令牌值，用于判断表单是否重复提交
        String session_uuid = (String) httpSession.getAttribute("session_uuid");
        httpSession.removeAttribute("session_uuid");
        if(!request_uuid.equals(session_uuid)) {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("redirect:/product/showProduct.do?productId=" + productId);
            return modelAndView;
        }
        //做此判断是因为placeHolder不能传过来后台
        if(number == null) {
            number = 1;
        }
        //根据productId和userId查询出对应的OrderItem，若有记录则把number改了，没有就新增一个OrderItem
        OrderItem orderItem = orderItemService.getOrderItemByProductIdAndUserId(productId,user.getId());
        if(orderItem != null) {
            int oldNumber = orderItem.getNumber();
            int newNumber = oldNumber + number;
            orderItem.setNumber(newNumber);
            orderItemService.updateOrderItem(orderItem);
        }else {
            //往数据库写入一个新的orderItem(未生成订单)
            OrderItem orderItem1 = new OrderItem();
            orderItem1.setId(null);
            orderItem1.setNumber(number);
            orderItem1.setProductId(productId);
            orderItem1.setOrderId(null);
            orderItem1.setUserId(user.getId());
            orderItemService.addOrderItem(orderItem1);
            orderItem = orderItemService.getOrderItemByProductIdAndUserId(productId,user.getId());
        }
        //更新购物车数量
        httpSession.removeAttribute("orderItemNumber");
        List<OrderItem> orderItem2 = orderItemService.getOrderItemByUserId(user.getId());
        int orderItemNumber = orderItem2.size();
        httpSession.setAttribute("orderItemNumber",orderItemNumber);
        //根据此订单的显示数据到下一个jsp
        Product product = productService.getProductById(productId);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("product",product);
        modelAndView.addObject("number",number);
        modelAndView.addObject("orderItem",orderItem);
        modelAndView.setViewName("anotherPage/settlement");
        return modelAndView;
    }

    //添加到购物车
    @RequestMapping("/orderItem/addToShoppingCart.do")
    @ResponseBody
    public String addOrderItem(Integer productId,Integer number,HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        //做此判断是因为placeHolder不能传过来后台
        if(number == null) {
            number = 1;
        }
        //根据productId和userId查询出对应的OrderItem，若有记录则把number改了，没有就新增一个OrderItem
        OrderItem orderItem = orderItemService.getOrderItemByProductIdAndUserId(productId,user.getId());
        if(orderItem != null) {
            int oldNumber = orderItem.getNumber();
            int newNumber = oldNumber + number;
            orderItem.setNumber(newNumber);
            orderItemService.updateOrderItem(orderItem);
        }else {
            //往数据库写入一个新的orderItem(未生成订单)
            OrderItem orderItem1 = new OrderItem();
            orderItem1.setId(null);
            orderItem1.setNumber(number);
            orderItem1.setProductId(productId);
            orderItem1.setOrderId(null);
            orderItem1.setUserId(user.getId());
            orderItemService.addOrderItem(orderItem1);
        }
        //更新购物车数量
        session.removeAttribute("orderItemNumber");
        List<OrderItem> orderItem2 = (List<OrderItem>) orderItemService.getOrderItemByUserId(user.getId());
        int orderItemNumber = orderItem2.size();
        session.setAttribute("orderItemNumber",orderItemNumber);
        return "AddToShoppingCartSuccessful";
    }


    //显示购物车
    @RequestMapping("/orderItem/showShoppingCart.do")
    public ModelAndView showShoppingCart(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        //查询此用户下的订单项
        List<OrderItem> orderItems = orderItemService.getOrderItemByUserId(user.getId());
        //根据订单项查询出product集合
        List<Product> products = new ArrayList<Product>();
        for(int i = 0;i < orderItems.size();i++) {
            Product product = productService.getProductById(orderItems.get(i).getProductId());
            products.add(product);
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("orderItems",orderItems);
        modelAndView.addObject("products",products);
        modelAndView.setViewName("/anotherPage/shoppingCart");
        return modelAndView;
    }

    //购物车付款
    @RequestMapping("/orderItem/payForShoppingCart.do")
    public ModelAndView payForShoppingCart(Integer[] orderItemId,Integer[] inputNumberValue) {
        Integer[] orderItemIds = orderItemId;
        Integer[] inputNumberValues = inputNumberValue;
        //根据orderItemIds修改对应的number值
        for(int i = 0;i < orderItemIds.length;i++) {
            orderItemService.updateNumberById(orderItemIds[i],inputNumberValues[i]);
        }
        //获取orderItem的数组
        OrderItem[] orderItems = new OrderItem[orderItemIds.length];
        for(int i = 0;i < orderItemIds.length;i++) {
            orderItems[i] = orderItemService.getOrderItemById(orderItemIds[i]);
        }
        //根据orderIds获取products
        List<Product> products = new ArrayList<Product>();
        for(int i = 0;i < orderItems.length;i++) {
            int productId = orderItems[i].getProductId();
            Product product = productService.getProductById(productId);
            products.add(product);
        }
        //获取价格总数
        float totalMoney = 0;
        for(int i = 0;i < orderItems.length;i++) {
            int number = orderItems[i].getNumber();
            float money = products.get(i).getPromotePrice();
            totalMoney = totalMoney + number * money;
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("orderItems",orderItems);
        modelAndView.addObject("products",products);
        modelAndView.addObject("totalMoney",totalMoney);
        modelAndView.setViewName("anotherPage/forebuy");
        return modelAndView;
    }


    //删除购物车的商品
    @RequestMapping("/orderItem/deleteFromShoppingCart.do")
    public ModelAndView deleteFormShoppingCart(Integer orderItemId,HttpServletRequest request) {
        //根据id删除订单项
        orderItemService.deleteOrderItemById(orderItemId);
        //更新购物车数量
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        session.removeAttribute("orderItemNumber");
        List<OrderItem> orderItem2 = orderItemService.getOrderItemByUserId(user.getId());
        int orderItemNumber = orderItem2.size();
        session.setAttribute("orderItemNumber",orderItemNumber);
        ModelAndView modelAndView = new ModelAndView("redirect:/orderItem/showShoppingCart.do");
        return modelAndView;
    }

}
