package com.zhangxin.tmall.controller;

import com.zhangxin.tmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

}
