package com.zhangxin.tmall.controller;

import com.zhangxin.tmall.pojo.Category;
import com.zhangxin.tmall.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("")
public class CategoryController {
   @Autowired
    private CategoryService categoryService;

}
