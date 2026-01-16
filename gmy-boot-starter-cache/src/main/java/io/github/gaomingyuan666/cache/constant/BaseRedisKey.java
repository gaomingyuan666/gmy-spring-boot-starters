package io.github.gaomingyuan666.cache.constant;

/**
 * redisKey抽象类
 *
 * @author gaomingyuan
 */
public abstract class BaseRedisKey {
    protected static String baseKey;

    public static String getKey(String key, Object... objects) {
        return baseKey + String.format(key, objects);
    }
}
