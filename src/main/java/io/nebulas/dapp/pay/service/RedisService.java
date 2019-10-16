package io.nebulas.dapp.pay.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * Desc:
 * User: nathan
 * Date: 2018-04-25
 */
@Service
public class RedisService {
    private static final String KEY_PREFIX_PATTERN = "dapp-pay:%s";

    @Autowired
    @Qualifier("stringRedisTemplate")
    private RedisTemplate<String, String> stringRedisTemplate;

    public void setAndExpired(String key, String value, long timeout, TimeUnit unit) {
        stringRedisTemplate.opsForValue().set(getRealKey(key), value, timeout, unit);
    }

    public String get(String key) {
        return stringRedisTemplate.opsForValue().get(getRealKey(key));
    }

    public long incrAndExpired(String key, long timeout, TimeUnit timeUnit) {
        String realKey = getRealKey(key);
        long r = stringRedisTemplate.opsForValue().increment(realKey, 1);
        stringRedisTemplate.expire(realKey, timeout, timeUnit);
        return r;
    }

    private String getRealKey(String key) {
        return String.format(KEY_PREFIX_PATTERN, key);
    }
}
