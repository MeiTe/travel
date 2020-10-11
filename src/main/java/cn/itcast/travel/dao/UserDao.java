package cn.itcast.travel.dao;

import cn.itcast.travel.domain.User;

public interface UserDao {

    //根据用户名查询是否存在用户
    public User findByUsername(String username);

    //把注册的用户信息保留到数据库中
    public void insertUser(User user);


    //查询是否存在
    User findByCode(String code);
    //修改状态码
    void updateStatus(User user);

    //判断用户是否存在
    User login(String username, String password);
}
