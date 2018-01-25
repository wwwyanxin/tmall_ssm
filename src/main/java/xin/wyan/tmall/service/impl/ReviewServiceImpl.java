package xin.wyan.tmall.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xin.wyan.tmall.mapper.ReviewMapper;
import xin.wyan.tmall.pojo.Review;
import xin.wyan.tmall.pojo.ReviewExample;
import xin.wyan.tmall.pojo.User;
import xin.wyan.tmall.service.ReviewService;
import xin.wyan.tmall.service.UserService;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {
    @Autowired(required = false)
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

    @Override
    public void update(Review review) {
        reviewMapper.updateByPrimaryKeySelective(review);
    }

    @Override
    public Review get(int id) {
        return reviewMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Review> list(int pid) {
        ReviewExample example = new ReviewExample();
        example.createCriteria().andPidEqualTo(pid);
        example.setOrderByClause("id desc");

        List<Review> result = reviewMapper.selectByExample(example);
        setUser(result);
        return result;
    }

    @Override
    public int getCount(int pid) {
        return list(pid).size();
    }

    private void setUser(List<Review> reviews) {
        for (Review review : reviews) {
            setUser(review);
        }
    }

    private void setUser(Review review) {
        int uid = review.getUid();
        User user = userService.get(uid);
        review.setUser(user);
    }
}
