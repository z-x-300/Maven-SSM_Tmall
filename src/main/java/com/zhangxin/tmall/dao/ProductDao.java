package com.zhangxin.tmall.dao;

import com.zhangxin.tmall.pojo.Product;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductDao {
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
    //根据name模糊查询商品（映射fiveImage/sixImage）
    public List<Product> getProductByName(String name);
    //修改商品库存和月销量
    public void updateStockAndSaleCountById(@Param("id") int id, @Param("number") int number);
    //库存不足自动添加库存
    public void updateStockById(int id);
}
