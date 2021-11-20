package com.redis_springboot;

import com.alibaba.fastjson.JSON;
import com.redis_springboot.util.RedisUtil;
import com.redis_springboot.util.RedisUtils;

import net.minidev.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;

@SpringBootTest
class RedisSpringbootApplicationTests {

    /**
     * redisTemplate 操作不同的数据类型，api与redis数据中的指令相同
     *
     * opsForValue  操作字符串，即String 类型
     * opsForList   操作List
     * opsForSet    操作Set
     * opsForHash   操作Hash
     * opsForZSet   操作ZSet,即有序set集合
     * opsForGeo    操作GEO
     * opsForHyperloglog
     *
     * redisTemplate 先指定需要操作的数据类型然后再接上需要进行的操作
     *
     * eg:  redisTemplate.opsForValue().set("name","刘佳俊");
     *
     */

    @Resource
    RedisTemplate<String,Object> redisTemplate;

    @Test
    void redisTest(){

        redisTemplate.opsForValue().set("name","刘佳俊");
        System.out.println(redisTemplate.opsForValue().get("name"));

        RedisUtils redisUtil = new RedisUtils();
        System.out.println(redisUtil.get("name"));

//        获取redis的连接对
//        RedisConnection connection = redisTemplate.getConnectionFactory().getConnection();
//        对redis数据库连接对象进行操作
//        connection.flushDb();

    }


    @Test
    void redisUtilTest(){
//        RedisUtils redisUtil = new RedisUtils();
//        System.out.println(redisUtil.get("name"));
        String name ="name";
        String json= JSON.toJSONString(name);

        System.out.println(redisTemplate.opsForValue().get("name"));
    }

}
