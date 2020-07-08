package com.zhangxin.tmall.service;

import org.apache.ibatis.annotations.Param;

public interface FiveImageService {
    //添加五张图片
    public void addFiveImageForAdmin( String littleImageUri,String bigImageUri,Integer productId);
    //删除图片
    public void deleteFiveImageForAdmin(int id);
}
