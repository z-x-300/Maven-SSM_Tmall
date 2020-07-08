package com.zhangxin.tmall.service;

import org.apache.ibatis.annotations.Param;

public interface SixImageService {
    //增加一个sixImage
    public void addSixImageForAdmin(String imageUri,Integer productId);
    //根据id删除sixImage
    public void deleteSixImageByIdForAdmin(int id);
}
