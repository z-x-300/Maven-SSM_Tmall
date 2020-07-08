package com.zhangxin.tmall.service;

import com.zhangxin.tmall.dao.FiveImageDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FiveImageImpl implements FiveImageService {

    @Autowired
    private FiveImageDao fiveImageDao;

    //添加五张图片
    public void addFiveImageForAdmin(String littleImageUri,String bigImageUri,Integer productId){
        fiveImageDao.addFiveImageForAdmin(littleImageUri,bigImageUri,productId);
    }

    //删除图片
    public void deleteFiveImageForAdmin(int id){
        fiveImageDao.deleteFiveImageForAdmin(id);
    }
}
