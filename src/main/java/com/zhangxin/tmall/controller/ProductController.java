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
    //查询商品
    @RequestMapping("/product/showProductByName.do")
    public ModelAndView showProductByName(int start,String searchMessage) {
        //获取总数
        List<Product> list = productService.getProductByName(searchMessage);
        PageInfo<Product> pageInfo = new PageInfo<Product>(list);
        //总数×5是因为一个product中有5个fiveimage
        int total = (int) pageInfo.getTotal() * 30;
        //判断边界
        if(start > total) {
            //获取最大的页数(81~89为8)
            start = total / 600 * 600;
        }
        if(start == total) {
            //（90为9，所以要减一）
            start = (total / 600 - 1) * 600;
        }
        if(start <= 0) {
            //判断最小边界
            start = 0;
        }
        //使用600去分页是因为一个product对应了5个fiveimage和6个sixImage，实际上是只有20个product
        PageHelper.offsetPage(start,600);
        //把获取到的searchMessage拿去模糊查询
        List<Product> products = productService.getProductByName(searchMessage);
        System.out.println(products.get(0).getId());
        System.out.println("start:" + start);
        System.out.println("total:" + total);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("products",products);
        modelAndView.addObject("searchMessage",searchMessage);
        modelAndView.addObject("start",start);
        modelAndView.addObject("total",total);
        modelAndView.setViewName("/anotherPage/search");
        return modelAndView;
    }

}
