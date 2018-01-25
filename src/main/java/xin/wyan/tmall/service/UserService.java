package xin.wyan.tmall.service;

import xin.wyan.tmall.pojo.User;

import java.util.List;

public interface UserService {
    void add(User user);

    void delete(int id);

    void update(User user);

    User get(int id);

    User get(String name, String password);

    List<User> list();

    boolean isExist(String name);
}
