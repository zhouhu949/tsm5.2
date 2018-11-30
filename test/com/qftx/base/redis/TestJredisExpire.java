package com.qftx.base.redis;


import com.qftx.base.util.redis.JedisUtil;
import redis.clients.jedis.Jedis;

/**
 * User: bxl
 * Date: 14-6-5
 * Time: 上午10:12
 */
public class TestJredisExpire {
    public static void main(String[] args) throws InterruptedException {
        Jedis jedis = JedisUtil.getInstance().getJedis();
        jedis.set("k1", "k234234234");
        System.out.println(jedis.get("k1"));
        System.out.println(jedis.ttl("k1"));
        jedis.expire("k1",0);
        Thread.sleep(6000);
        System.out.println(jedis.get("k1"));


    }


}
