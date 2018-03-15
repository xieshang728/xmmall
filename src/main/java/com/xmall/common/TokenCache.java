package com.xmall.common;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author xies
 * @date 2018/1/22
 */
@Slf4j
public class TokenCache {

    public static final String TOKEN_PREFIX = "token_";

    private static LoadingCache<String, String> loadingCache = CacheBuilder
            .newBuilder()
            .initialCapacity(1000)
            .maximumSize(10000)
            .expireAfterAccess(30, TimeUnit.MINUTES)
            .build(new CacheLoader<String, String>() {
                @Override
                public String load(String s) throws Exception {
                    return "null";
                }
            });


    public static void setKey(String key, String value) {
        loadingCache.put(key, value);
    }

    public static String getKey(String key) {
        try {
            String value = loadingCache.get(key);
            if (Const.NULL_STR.equals(value)) {
                return null;
            }
            return value;
        } catch (Exception e) {
            log.error("getKey exception: " + e.toString());
        }
        return null;
    }

}
