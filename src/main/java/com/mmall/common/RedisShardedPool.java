package com.mmall.common;

import com.mmall.util.PropertiesUtil;
import redis.clients.jedis.*;
import redis.clients.util.Hashing;
import redis.clients.util.Sharded;

import java.util.ArrayList;
import java.util.List;

/**
 * create by YuWen
 */
public class RedisShardedPool {

    //    private static JedisPool pool;      //设置为static 保证jedis连接池在tomcat启动时就被加载进来
    private static ShardedJedisPool pool;   //shareded jedis 连接池
    private static Integer maxTotal = PropertiesUtil.getIntProperty("redis.max.total", 20);//最大连接数
    private static Integer maxIdle = PropertiesUtil.getIntProperty("redis.max.idle", 10);//Jedis连接池中最多有多少个状态为Idel(空闲)的jedis实例
    private static Integer minIdle = PropertiesUtil.getIntProperty("redis.min.idle", 2);//Jedis连接池中最少有多少个状态为Idel(空闲)的jedis实例
    /*在jedis连接池中取出实例时(和redis server进行通信)是否要进行验证操作,如果赋值为true则得到的jedis实例为可用的*/
    private static Boolean testOnBorrow = PropertiesUtil.getBooleanProperty("redis.test.borrow", false);
    /*在jedis连接池中还回实例时(和redis server进行通信)是否要进行验证操作,如果赋值为true则放回jedis pool的jedis实例为可用的*/
    private static Boolean testOnReturn = PropertiesUtil.getBooleanProperty("redis.test.return", false);

    private static String redis1Ip = PropertiesUtil.getProperty("redis1.ip");
    private static Integer redis1Port = Integer.valueOf(PropertiesUtil.getProperty("redis1.port"));

    private static String redis2Ip = PropertiesUtil.getProperty("redis2.ip");
    private static Integer redis2Port = Integer.valueOf(PropertiesUtil.getProperty("redis2.port"));


    private static void initPool() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(maxTotal);
        config.setMaxIdle(maxIdle);
        config.setMinIdle(minIdle);

        config.setTestOnBorrow(testOnBorrow);
        config.setTestOnReturn(testOnReturn);

        config.setBlockWhenExhausted(true); //连接耗尽时是否阻塞,false会抛出异常, true会阻塞直到超时,会抛出超时异常。源码中默认为true,此条不必设置
//        pool = new JedisPool(config, redisIp, redisPort, 1000 * 2);    //超时时间单位为毫秒

        JedisShardInfo info1 = new JedisShardInfo(redis1Ip, redis1Port, 1000 * 2);
        //如果redis有密码  调用info2.setPassword("");
        JedisShardInfo info2 = new JedisShardInfo(redis2Ip, redis2Port, 1000 * 2);

        List<JedisShardInfo> jedisShardInfoList = new ArrayList<JedisShardInfo>(2);
        jedisShardInfoList.add(info1);
        jedisShardInfoList.add(info2);

        //本身redis提供两种分片策略 md5和murmur_hash(对应一致性算法,默认为murmur_hash)
        pool = new ShardedJedisPool(config, jedisShardInfoList, Hashing.MURMUR_HASH,
                Sharded.DEFAULT_KEY_TAG_PATTERN);
    }

    static {
        initPool();
    }

    public static ShardedJedis getJedis() {
        return pool.getResource();
    }

    public static void returnResource(ShardedJedis jedis) {
        pool.returnResource(jedis);
    }

    public static void returnBrokenResource(ShardedJedis jedis) {
        pool.returnBrokenResource(jedis);
    }




    public static void main(String[] args) {
        ShardedJedis jedis = pool.getResource();
        for (int i = 0; i < 10; i++) {
            jedis.set("key" + i, "value" + i);
        }
        returnResource(jedis);
//        pool.destroy();
        System.out.println("Program is end");
    }


}
