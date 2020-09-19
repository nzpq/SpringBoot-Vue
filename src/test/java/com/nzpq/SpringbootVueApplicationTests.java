package com.nzpq;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

@SpringBootTest
class SpringbootVueApplicationTests {

    @Autowired
    private StringRedisTemplate template;

    /**
     * 测试是否可以连接上redis
     */
    @Test
    public void test(){
        template.opsForValue().set("name","test");
    }

}
