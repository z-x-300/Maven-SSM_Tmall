package com.zhangxin.tmall.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhangxin.tmall.pojo.Order;
import com.zhangxin.tmall.pojo.Product;
import com.zhangxin.tmall.pojo.Review;
import com.zhangxin.tmall.pojo.User;
import com.zhangxin.tmall.service.OrderService;
import com.zhangxin.tmall.service.ProductService;
import com.zhangxin.tmall.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Controller
public class ReviewController {
    @Autowired
    ReviewService reviewService;

    @Autowired
    OrderService orderService;

    @Autowired
    ProductService productService;

    //增加一个评论
    @RequestMapping("/review/addreview.do")
    public ModelAndView addreview(Integer productId, Integer orderId, String reviewValue, String request_uuid, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        //session的令牌值，用于判断表单是否重复提交
        String session_uuid = (String) session.getAttribute("session_uuid");
        if(request_uuid.equals(session_uuid)) {
            //根据productId获取product
            Product product = productService.getProductById(productId);
            //根据orderId获取order
            Order order = orderService.getOrderById(orderId);
            //获取评论总数
            List<Review> list = reviewService.getReviewByProductId(productId);
            PageInfo<Review> pageInfo = new PageInfo<Review>(list);
            int total = (int) pageInfo.getTotal();
            //获取评论
            PageHelper.offsetPage(0,20);
            List<Review> reviews = reviewService.getReviewByProductId(productId);
            ModelAndView mav = new ModelAndView();
            mav.addObject("order",order);
            mav.addObject("product",product);
            mav.addObject("reviews",reviews);
            mav.addObject("total",total);
            mav.addObject("addReviewFail","addReviewFail");
            mav.setViewName("/anotherPage/review");
            return mav;
        }
        //增加评论
        Review review = new Review();
        review.setContent(reviewValue);
        review.setCreateDate(new Date());
        review.setUserId(user.getId());
        review.setProductId(productId);
        reviewService.addReview(review);
        //修改订单中的状态
        String status = "已评价";
        orderService.updateStatusById(orderId,status);
        //根据productId获取product
        Product product = productService.getProductById(productId);
        //根据orderId获取order
        Order order = orderService.getOrderById(orderId);
        //获取评论总数
        List<Review> list = reviewService.getReviewByProductId(productId);
        PageInfo<Review> pageInfo = new PageInfo<Review>(list);
        int total = (int) pageInfo.getTotal();
        //获取评论
        PageHelper.offsetPage(0,20);
        List<Review> reviews = reviewService.getReviewByProductId(productId);
        //设置令牌值，防止刷新页面导致表单重复提交
        session.setAttribute("session_uuid","uuid");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("order",order);
        modelAndView.addObject("product",product);
        modelAndView.addObject("total",total);
        modelAndView.addObject("reviews",reviews);
        modelAndView.addObject("addReviewSuccessful","addReviewSuccessful");
        modelAndView.setViewName("/anotherPage/review");
        return modelAndView;
    }
}
