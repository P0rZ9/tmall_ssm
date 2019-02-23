package cn.test.tmall.service;


import cn.test.tmall.mapper.ReviewMapper;
import cn.test.tmall.pojo.Review;
import cn.test.tmall.pojo.ReviewExample;
import cn.test.tmall.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    ReviewMapper reviewMapper;

    @Autowired
    UserService userService;

    @Override
    public void add(Review review) {
        reviewMapper.insert(review);
    }

    @Override
    public void delete(int id) {
        reviewMapper.deleteByPrimaryKey(id);
    }


    public void update(Review review) {
        reviewMapper.updateByPrimaryKeySelective(review);
    }

    @Override
    public Review get(int id) {
        return reviewMapper.selectByPrimaryKey(id);
    }

    @Override
    public int getCount(int pid){
        return list(pid).size();

    }

    @Override
    public List<Review> list(int pid) {
        ReviewExample reviewExample = new ReviewExample();
        reviewExample.createCriteria().andPidEqualTo(pid);
        List<Review> reviews = reviewMapper.selectByExample(reviewExample);

        return  reviews;
    }

    private void setUser(Review review){
        int uid = review.getUid();
        User user = userService.get(uid);
        review.setUser(user);
    }

    public void setUser(List<Review> reviews){
        for(Review review : reviews){
            setUser(review);
        }
    }





}
