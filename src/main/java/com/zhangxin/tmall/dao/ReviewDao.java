package com.zhangxin.tmall.dao;

import com.zhangxin.tmall.pojo.Review;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewDao {

    //根据product的id查询其下的所有评论（映射user）
    public List<Review> getReviewByProductId(int productId);
    //增加一个评论
    public void addReview(Review review);
}
