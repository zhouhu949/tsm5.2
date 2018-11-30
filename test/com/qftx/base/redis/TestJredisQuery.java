package com.qftx.base.redis;


import com.qftx.base.util.redis.JedisUtil;
import redis.clients.jedis.Jedis;

import java.util.Set;

/**
 * User: bxl
 * Date: 14-6-5
 * Time: 上午10:12
 */
public class TestJredisQuery {
    public static void main(String[] args) throws InterruptedException {
        Jedis jedis = JedisUtil.getInstance().getJedis();
        System.out.println(jedis.get("k1"));
        jedis.set("k1", "k234234234");

/*
        jedis.expire("k1", 5);
        Thread.sleep(6000);
        System.out.println(jedis.get("k1"));*/
        Set<String> key= jedis.keys("k*");
        for (String s : key) {
          System.out.println(s+"="+(jedis.ttl(s)));
           // System.out.println((jedis.get(s)));
          //  if(s.startsWith("tp_")){
               // jedis.del(s);
          //  }
        //  jedis.del(s) ;
        }

        //  System.out.println(jedis.del("k1"));
      // jedis.del("c7c2c3ff511d4543aebedd42feb17ffb");

    }


}
