package cn.test.tmall.service;

import cn.test.tmall.pojo.Review;

import java.util.List;

public interface ReviewService {

    void add(Review review);
    void delete(int id);
    void update(Review review);
    Review get(int id);

    List<Review> list(int pid);

    //根据产品获取评论数量
    int getCount(int pid);

}
