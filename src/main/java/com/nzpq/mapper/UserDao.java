package com.nzpq.mapper;

import com.nzpq.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao {
    /**
     * 保存用户
     * @param user
     */
    void save(User user);

    /**
     * 根据用户名查找
     * 用处：1、防止同一用户名重复注册
     *       2、登录，先根据用户输入的用户名查询，判单该用户名是否存在，若存在，则在比较用户输入的密码和数据库中的密码是否一致
     * @param username
     * @return
     */
    User findUserByUsername(String username);
}
