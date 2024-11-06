package com.scan.keeper.db;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class MemCache {

    /**
     * 
     */
    private static final Map<String, Object> CACHE_MAP = new ConcurrentHashMap<String, Object>();

    /**
     * 12
     */
    public static final long CACHE_HOLD_TIME_12H = 12 * 60 * 60 * 1000L;

    /**
     * 24
     */
    public static final long CACHE_HOLD_TIME_24H = 24 * 60 * 60 * 1000L;
    
    public static final long CACHE_HOLD_TIME_1H = 60 * 60 * 1000L;

    public static final long CACHE_HOLD_TIME_10M = 10 * 60 * 1000L;
    
    public static final long CACHE_HOLD_TIME_5M = 5 * 60 * 1000L;
    
    public static final long CACHE_HOLD_TIME_30S = 30 * 1000L;

    /**
     * ，10
     *
     * @param cacheName
     * @param obj
     */
    public static void put(String cacheName, Object obj) {
        put(cacheName, obj, CACHE_HOLD_TIME_10M);
    }

    /**
     * ，holdTime
     *
     * @param cacheName
     * @param obj
     * @param holdTime
     */
    public static void put(String cacheName, Object obj, long holdTime) {
        CACHE_MAP.put(cacheName, obj);
        //
        CACHE_MAP.put(cacheName + "_HoldTime", System.currentTimeMillis() + holdTime);
    }

    /**
     * 
     *
     * @param cacheName
     * @return
     */
    public static Object get(String cacheName) {
        if (checkCacheName(cacheName)) {
            return CACHE_MAP.get(cacheName);
        }
        return null;
    }

    /**
     * 
     */
    public static void removeAll() {
        CACHE_MAP.clear();
    }

    /**
     * 
     *
     * @param cacheName
     */
    public static void remove(String cacheName) {
        CACHE_MAP.remove(cacheName);
        CACHE_MAP.remove(cacheName + "_HoldTime");
    }

    /**
     * ，
     * ，false
     * ，，false
     *
     * @param cacheName
     * @return
     */
    public static boolean checkCacheName(String cacheName) {
        Long cacheHoldTime = (Long) CACHE_MAP.get(cacheName + "_HoldTime");
        if (cacheHoldTime == null || cacheHoldTime == 0L) {
            return false;
        }
        if (cacheHoldTime < System.currentTimeMillis()) {
            remove(cacheName);
            return false;
        }
        return true;
    }
}