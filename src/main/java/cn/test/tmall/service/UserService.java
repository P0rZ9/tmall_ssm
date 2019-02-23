package cn.test.tmall.service;

import cn.test.tmall.pojo.User;

import java.util.List;

public interface UserService {

    List<User> list();

    User get(int id);
    void add(User user);
    void delete(int id);
    void update(User user);
    boolean isExist(String name);

    public User get(String name,String password);



}
