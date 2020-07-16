package com.zhangxin.tmall.service;

import com.zhangxin.tmall.pojo.Product;

import java.util.List;

public interface ProductService {
    //根据categoryId查询所有的商品
    public List<Product> getAllProductByCategoryIdForAdmin(int categoryId);
    //增加一个商品
    public void addProductForAdmin(Product product);
    //修改商品
    public void updateProductForAdmin(Product product);
    //根据id删除商品
    public void deleteProductByIdForAdmin(int id);
    //根据id获取商品（映射fiveImage/sixImage）
    public Product getProductById(int id);
    //根据id修改库存和月销量
    public void updateStockAndSaleCountById(int id,int number);
    //库存不足自动添加库存
    public void updateStockById(int id);
}
