package com.zhangxin.tmall.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhangxin.tmall.pojo.Product;
import com.zhangxin.tmall.pojo.Property;
import com.zhangxin.tmall.pojo.PropertyValue;
import com.zhangxin.tmall.pojo.Review;
import com.zhangxin.tmall.service.ProductService;
import com.zhangxin.tmall.service.PropertyService;
import com.zhangxin.tmall.service.PropertyValueService;
import com.zhangxin.tmall.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private PropertyService propertyService;

    @Autowired
    private PropertyValueService propertyValueService;

    @Autowired
    private ReviewService reviewService;


    //获取商品并显示
    @RequestMapping("/product/showProduct.do")
    public ModelAndView showProduct(Integer productId){
        //获取商品信息
        Product product =productService.getProductById(productId);
        //获取属性
        List<Property> propertyList = propertyService.getPropertyByCategoryId(product.getCategoryId());
        //获取属性值
        List<PropertyValue> propertyValueList =propertyValueService.getPropertyValueByProductId(productId);

        //获取评论
        List<Review> reviewList = reviewService.getReviewByProductId(productId);
        PageInfo<Review> pageInfo =new PageInfo<Review>(reviewList);
        int total =(int)pageInfo.getTotal();

        PageHelper.offsetPage(0,20);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("product",product);
        modelAndView.addObject("reviews",reviewList);
        modelAndView.addObject("total",total);
        modelAndView.addObject("properties",propertyList);
        modelAndView.addObject("propertyValues",propertyValueList);
        modelAndView.setViewName("productPage/productPage");
        return modelAndView;

    }


}
