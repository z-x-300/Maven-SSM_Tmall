package com.zhangxin.tmall.service;

import com.zhangxin.tmall.pojo.Review;

import java.util.List;

public interface ReviewService {

    //根据product的id查询其下的所有评论（映射user）
    public List<Review> getReviewByProductId(int productId);
}
