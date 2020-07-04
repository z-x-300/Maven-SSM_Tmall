package com.zhangxin.tmall.controller;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhangxin.tmall.pojo.Category;
import com.zhangxin.tmall.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class AdminController {
    @Autowired
    private CategoryService categoryService;

    @RequestMapping("/admin/showCategory.do")
    public ModelAndView showCategory(Integer start){
        //获取总数
        List<Category> categoryList = categoryService.getAllCategoryForAdmin();
        PageInfo<Category> pageInfon = new PageInfo<Category>(categoryList);
        int total =(int) pageInfon.getTotal();
        //判断边界
        if(start>total){
            start =total/5*5;
        }
        if(start==total){
            start =(total/5-1)*5;
        }
        if (start<=0){
            //判断最小边界
            start=0;
        }
        PageHelper.offsetPage(start,5);
        List<Category> categories = categoryService.getAllCategoryForAdmin();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("categorys",categories);
        modelAndView.addObject("start",start);
        modelAndView.addObject("total",total);
        modelAndView.setViewName("forward:/admin/jsp/categoryManager.jsp");
        return modelAndView;
    }
}
