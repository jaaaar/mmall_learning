package com.mmall.common;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * create by YuWen
 */
@Slf4j
public class TokenCache {

    public static final String TOKEN_PREFIX = "token_";

    //设置缓存的初始化容量1000  缓存最大容量为  当超出这个容量的时候guava会使用LRU(最少使用)算法来移除缓存项
    //有效时间为12小时
    //设置缓存的初始化容量1000  缓存最大容量为  当超出这个容量的时候guava会使用LRU(最少使用)算法来移除缓存项
    //有效时间为12小时
    private static LoadingCache<String, String> localCache = CacheBuilder.newBuilder().
            initialCapacity(1000).maximumSize(10000).expireAfterAccess(12, TimeUnit.HOURS).
            build(new CacheLoader<String, String>() {
                //默认的数据加载实现,当调用get取值时,如果key没有对应的值,就调用此方法进行加载
                @Override
                public String load(String s) throws Exception {
                    return "null";
                }
            });

    public static void setKey(String key, String value) {
        localCache.put(key, value);
    }

    public static String getKey(String key) {
        String value = null;
        try {
            value = localCache.get(key);
            if ("null".equals(value)) {
                return null;
            }
            return value;
        } catch (ExecutionException e) {
            log.error("localCache get Error", e);
            return null;
        }
    }
}
