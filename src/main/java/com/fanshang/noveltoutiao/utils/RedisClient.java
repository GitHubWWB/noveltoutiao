package com.fanshang.noveltoutiao.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedisClient {

    @Autowired
	private StringRedisTemplate redisTemplate;


	 /**
     * 通过字符串key获取值
     * @param key 键
     * @return 值
     */
    public String get(String key){
        return key==null?null:redisTemplate.opsForValue().get(key);
    }


    /**
     * 普通缓存放入
     * @param key 键
     * @param value 值
     * @return true成功 false失败
     */
    public boolean set(String key,String value) {
         try {
            redisTemplate.opsForValue().set(key, value, 24, TimeUnit.HOURS);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    public boolean delete(String key){
        boolean flag = false;
        try {
            flag = redisTemplate.delete(key);
        } catch (Exception e){
            e.printStackTrace();
        }

        return flag;
    }

}
