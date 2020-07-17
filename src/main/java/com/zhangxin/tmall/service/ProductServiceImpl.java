package com.zhangxin.tmall.service;

import com.zhangxin.tmall.dao.ProductDao;
import com.zhangxin.tmall.pojo.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;


    //根据categoryId查询所有的商品
    public List<Product> getAllProductByCategoryIdForAdmin(int categoryId){
        List<Product> productList =productDao.getAllProductByCategoryIdForAdmin(categoryId);
        return productList;
    }

    //增加一个商品
    public void addProductForAdmin(Product product){
        productDao.addProductForAdmin(product);
    }

    //修改商品
    public void updateProductForAdmin(Product product){
        productDao.updateProductForAdmin(product);
    }

    //根据id删除商品
    public void deleteProductByIdForAdmin(int id){
        productDao.deleteProductByIdForAdmin(id);
    }

    //根据id获取商品（映射fiveImage/sixImage）
    public Product getProductById(int id){
        Product product=productDao.getProductById(id);
        return product;
    }

    //根据id修改库存和月销量
    public void updateStockAndSaleCountById(int id,int number){
        productDao.updateStockAndSaleCountById(id,number);
    }

    //库存不足自动添加库存
    public void updateStockById(int id){
        productDao.updateStockById(id);
    }

    //根据name模糊查询商品（映射fiveImage/sixImage）
    public List<Product> getProductByName(String name){
        List<Product> productList =productDao.getProductByName(name);
        return productList;
    }
}
