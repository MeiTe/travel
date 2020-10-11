package cn.itcast.travel.service;

import cn.itcast.travel.domain.User;

public interface UserService {
    boolean register(User user);
    boolean active(String code);
    //判断用户是否存在
    User login(User loginUser);
}
