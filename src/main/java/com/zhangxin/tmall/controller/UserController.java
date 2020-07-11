package com.zhangxin.tmall.controller;

import com.zhangxin.tmall.pojo.OrderItem;
import com.zhangxin.tmall.pojo.User;
import com.zhangxin.tmall.service.OrderItemService;
import com.zhangxin.tmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private OrderItemService orderItemService;

    //登录
    @RequestMapping("/user/login.do")
    public ModelAndView Login(String name, String password, HttpServletRequest request) {
        User user = userService.getUserByNameAndPassword(name, password);
        ModelAndView modelAndView = new ModelAndView();
        if (user != null) {
            Date date = new Date();
            userService.updateLoginDate(user.getId(), date);
            HttpSession httpSession = request.getSession();
            httpSession.setAttribute("user", user);

            //更新购物车数量
            httpSession.removeAttribute("orderItemNumber");
            List<OrderItem> orderItemList = orderItemService.getOrderItemByUserId(user.getId());
            int orderItemNumber = orderItemList.size();
            httpSession.setAttribute("orderItemNumber", orderItemNumber);

            modelAndView.setViewName("redirect:/index.jsp");
            return modelAndView;
        } else {
            modelAndView.addObject("message", "error");
            modelAndView.setViewName("anotherPage/login");
            return modelAndView;

        }
    }

    //注册
    @RequestMapping("/user/register.do")
    public ModelAndView Register(String name, String password1, String password2) {
        ModelAndView modelAndView = new ModelAndView();
        if (name.equals("")) {
            modelAndView.addObject("name","error");
            modelAndView.setViewName("anotherPage/register");
            return modelAndView;
        } else if (password1.equals("") || password2.equals("")) {
            modelAndView.addObject("password","error");
            modelAndView.setViewName("anotherPage/register");
            return modelAndView;
        } else if (!password1.equals(password2)) {
            modelAndView.setViewName("anotherPage/register");
            return modelAndView;
        } else {
            //判断数据库是否存在该用户
            User user = userService.getUserByUserName(name);
            //该用户已经存在
            if (user != null) {
                modelAndView.addObject("user", user);
                modelAndView.setViewName("anotherPage/register");
                return modelAndView;
            } else {
                User user1 = new User();
                user1.setName(name);
                user1.setPassword(password1);
                userService.insertUser(user1);
                modelAndView.setViewName("anotherPage/successRegister");
                return modelAndView;
            }
        }
    }
}
