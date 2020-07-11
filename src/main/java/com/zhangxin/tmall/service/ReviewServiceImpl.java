package com.zhangxin.tmall.service;

import com.zhangxin.tmall.dao.ReviewDao;
import com.zhangxin.tmall.pojo.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService{
    @Autowired
    private ReviewDao reviewDao;

    //根据product的id查询其下的所有评论（映射user）
    public List<Review> getReviewByProductId(int productId){
        List<Review> reviewList =reviewDao.getReviewByProductId(productId);
        return reviewList;
    }
}
