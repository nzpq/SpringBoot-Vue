package com.nzpq.service;

import com.nzpq.entity.User;

public interface UserService {
    /**
     * 注册
     * @param user
     */
    void register(User user);

    /**
     * 登录
     * @param user
     * @return
     */
    User login(User user);
}
