package xin.wyan.tmall.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xin.wyan.tmall.mapper.UserMapper;
import xin.wyan.tmall.pojo.User;
import xin.wyan.tmall.pojo.UserExample;
import xin.wyan.tmall.service.UserService;

import java.util.List;
@Service
public class UserServiceImpl implements UserService {
    @Autowired(required = false)
    UserMapper userMapper;

    @Override
    public void add(User user) {
        userMapper.insert(user);
    }

    @Override
    public void delete(int id) {
        userMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(User user) {
        userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public User get(int id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<User> list() {
        UserExample example = new UserExample();
        example.setOrderByClause("id desc");
        return userMapper.selectByExample(example);
    }
}
