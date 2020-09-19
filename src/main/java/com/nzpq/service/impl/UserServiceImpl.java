package com.nzpq.service.impl;

import com.nzpq.entity.User;
import com.nzpq.mapper.UserDao;
import com.nzpq.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.Date;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public void register(User user) {

        User u = userDao.findUserByUsername(user.getUsername());

        logger.info("findUserByUsername -->  "+u);
        if(u == null){
            user.setStatus("已激活");
            user.setRegisterTime(new Date());
            userDao.save(user);
        }else {
            throw new RuntimeException("用户已存在");
        }


    }

    @Override
    public User login(User user) {
        User u = userDao.findUserByUsername(user.getUsername());

        if(ObjectUtils.isEmpty(u)){
           //用户名不存在
            throw new RuntimeException("用户名错误！");
        }else{
            //用户名存在，在比较密码
            if(u.getPassword().equals(user.getPassword())){
                //密码正确
                return u;
            }else{
                //密码错误
                throw new RuntimeException("密码错误！");
            }
        }

    }


}
