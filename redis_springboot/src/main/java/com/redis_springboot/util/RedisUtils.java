package com.redis_springboot.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

public class RedisUtils {
    public static void main(String[] args) {
        RedisUtil redisUtil = new RedisUtil();
        System.out.println(redisUtil.get("name"));
    }
    @Autowired
    RedisTemplate<String,Object> redisTemplate;

    public Object get(String key){
        System.out.println(key);
        System.out.println("".equals(key));



        return key==null ?  redisTemplate.opsForValue().get(key) : null;
    }

}
