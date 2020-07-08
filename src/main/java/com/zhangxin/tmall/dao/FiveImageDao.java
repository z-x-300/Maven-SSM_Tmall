package com.zhangxin.tmall.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FiveImageDao {
    //添加五张图片
    public void addFiveImageForAdmin(@Param("littleImageUri") String littleImageUri,@Param("bigImageUri") String bigImageUri,
                                     @Param("productId") Integer productId);
    //删除图片
    public void deleteFiveImageForAdmin(int id);
}
