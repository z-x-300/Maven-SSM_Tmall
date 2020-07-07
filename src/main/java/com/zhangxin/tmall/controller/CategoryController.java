package com.zhangxin.tmall.controller;

import com.zhangxin.tmall.pojo.Category;
import com.zhangxin.tmall.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("")
public class CategoryController {
   @Autowired
    private CategoryService categoryService;
    //根据id获取对应的商品，并跳到商品分类页面
    @RequestMapping("/category/showProducts.do")
    public ModelAndView categoryShowProducts(int categoryId) {
        Category category = categoryService.getCategoryById(categoryId);
        ModelAndView mav = new ModelAndView();
        mav.addObject("category",category);
        mav.setViewName("classificationPage/classificationPage");
        return mav;
    }
}
