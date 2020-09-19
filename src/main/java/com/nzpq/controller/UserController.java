package com.nzpq.controller;

import com.nzpq.entity.User;
import com.nzpq.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin //允许跨域
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    //日志
    Logger logger = LoggerFactory.getLogger(UserController.class);

    /**
     * 用户注册
     * @param user
     * @return
     */
    @PostMapping("/register")
    public Map<String,Object> register(@RequestBody User user){

        logger.info("用户信息 --> " + user);
        Map<String,Object> map = new HashMap<>();
        //调用业务层方法
        try {
            //用户填写的信息为空，抛出异常
            if(user.getUsername() == null || "".equals(user.getUsername()) || user.getPassword() == null || "".equals(user.getPassword())){
                throw new RuntimeException("用户输入信息为空");
            }
            userService.register(user);
            map.put("state",true);
            map.put("message","注册成功，请登录！");
        } catch (Exception e) {
            map.put("state",false);
            map.put("message","注册失败! "+e.getMessage());
            logger.error(e.toString());
        }
        logger.info("返回信息 --> "+map.toString());
        return map;
    }

    /**
     * 用户登录
     * @return
     */
    @PostMapping("/login")
    public Map<String,Object> login(@RequestBody User user){

        Map<String,Object> map = new HashMap<>();
        try {
            User u = userService.login(user);
            map.put("user",u);//存储登陆成功的用户信息
            map.put("state",true);
            map.put("message","登陆成功");
        } catch (Exception e) {
            map.put("state",false);
            map.put("message",e.getMessage());
            logger.error(e.toString());
        }
        return map;
    }
}
