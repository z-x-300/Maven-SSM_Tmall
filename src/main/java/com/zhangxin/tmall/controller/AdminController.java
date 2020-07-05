package com.zhangxin.tmall.controller;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhangxin.tmall.pojo.Category;
import com.zhangxin.tmall.pojo.Property;
import com.zhangxin.tmall.service.CategoryService;
import com.zhangxin.tmall.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.IIOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
public class AdminController {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private PropertyService propertyService;

    @RequestMapping("/admin/showCategory.do")
    public ModelAndView showCategory(Integer start) {
        //获取总数
        List<Category> list = categoryService.getAllCategoryForAdmin();
        PageInfo<Category> pageInfo = new PageInfo<Category>(list);
        int total = (int) pageInfo.getTotal();
        //判断边界
        if(start > total) {
            start = total / 5 * 5;
        }
        if(start == total) {
            start = (total / 5 - 1) * 5;
        }
        if(start <= 0) {
            //判断最小边界
            start = 0;
        }
        PageHelper.offsetPage(start,5);
        List<Category> categorys = categoryService.getAllCategoryForAdmin();
        ModelAndView mav = new ModelAndView();
        mav.addObject("categorys",categorys);
        mav.addObject("start",start);
        mav.addObject("total",total);
        mav.setViewName("forward:/admin/jsp/categoryManager.jsp");
        return mav;
    }


    //添加一个category
    @RequestMapping(value = {"/admin/addCategory.do", "post"})
    public ModelAndView addCategory(String categoryName, Part categoryPicture, HttpServletRequest request) {
        //获取文件名
        String categoryPictureName = categoryPicture.getSubmittedFileName();
        System.out.println(categoryName);
        //定义保存的文件名
        String fileName = null;
        //获取tomcat工作目录
        String tomcatPath = request.getSession().getServletContext().getRealPath("image/classificationPage");
        System.out.println(tomcatPath);
        if (categoryPictureName.endsWith("jpg")) {
            fileName = System.currentTimeMillis() + ".jpg";
        } else if (categoryPictureName.endsWith(".png")) {
            fileName = System.currentTimeMillis() + ".png";
        } else {
            ModelAndView modelAndView = new ModelAndView("redirect:/admin/showCategory.do?message=error&start=0");
            return modelAndView;
        }
        //判断tomcat下的upload下的刚上传的那个图片
        File tomcatPathFolder = new File(tomcatPath);
        if (!tomcatPathFolder.exists()) {
            tomcatPathFolder.mkdir();
        }
        File tomcatPathFile = new File(tomcatPathFolder, fileName);
        try {
            categoryPicture.write(tomcatPathFile.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        String imageUrl = "/SSMTmall/image/classificationPage/" + fileName;
        categoryService.addCategoryForAdmin(categoryName, imageUrl);
        //跳转到最后一面
        List<Category> categoryList = categoryService.getAllCategoryForAdmin();
        PageInfo<Category> pageInfo = new PageInfo<Category>(categoryList);
        int start = (int) pageInfo.getTotal() + 1;
        //使用重定向防止表单重复提交
        ModelAndView mav = new ModelAndView("redirect:/admin/showCategory.do?message=success&start=" + start);
        return mav;
    }

    //更新category
    @RequestMapping("/admin/updateCategory.do")
    public ModelAndView updateCategory(Integer categoryId, String categoryName, Integer start) {
        categoryService.updateCategoryForAdmin(categoryId, categoryName);
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/showCategory.do?&start=" + start);
        return modelAndView;
    }

    //删除category
    @RequestMapping("/admin/deleteCategory.do")
    public ModelAndView deleteCategory(Integer categoryId) {
        int start = categoryService.getCategoryLocationById(categoryId);
        categoryService.deleteCategoryForAdmin(categoryId);
        start = categoryId / 5 * 5;
        if (categoryId % 5 != 0) {
            start = categoryId / 5 * 5;
        } else {
            start = (categoryId / 5 - 1) * 5;
        }
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/showCategory.do?&start=" + start);
        return modelAndView;
    }

    //显示属性
    @RequestMapping("/admin/showProperty.do")
    public ModelAndView showProperty(int start,int categoryId){
        //获取总数
        List<Property> propertyList =propertyService.getPropertyByCategoryId(categoryId);
        PageInfo<Property> propertyPageInfo = new PageInfo<Property>(propertyList);
        int total =(int) propertyPageInfo.getTotal();
        //判断边界
        if(start > total) {
            start = total / 5 * 5;
        }
        if(start == total) {
            start = (total / 5 - 1) * 5;
        }
        if(start <= 0) {
            //判断最小边界
            start = 0;
        }
        PageHelper.offsetPage(start,5);
        List<Property> properties =propertyService.getPropertyByCategoryId(categoryId);
        Category category =categoryService.getCategoryByIdForAdmin(categoryId);
        ModelAndView modelAndView =new ModelAndView();
        modelAndView.addObject("properties",properties);
        modelAndView.addObject("start",start);
        modelAndView.addObject("total",total);
        modelAndView.addObject("category",category);
        modelAndView.setViewName("forward:/admin/jsp/propertyManager.jsp");
        return modelAndView;
    }
}
