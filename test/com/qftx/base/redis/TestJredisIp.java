package com.qftx.base.redis;


import com.qftx.base.util.redis.JedisUtil;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Set;

/**
 * User: bxl
 * Date: 14-6-5
 * Time: 上午10:12
 */
public class TestJredisIp {
    public static void main(String[] args) throws InterruptedException {
        Jedis jedis = JedisUtil.getInstance().getJedis();
        JedisUtil.Hash hash = JedisUtil.getInstance().new Hash();
        //  hash.hset("test", "key", "test1");
        hash.hdel("test");
        HashMap map = new HashMap();
        map.put("key1", "k1");
        map.put("key2", "k2");
        hash.hmset("test", map);
        hash.hset("test", "key1", "234234");
        String key = hash.hget("test", "key1");
        System.out.println(key);

    }


}
