package cn.leon.base;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.concurrent.TimeUnit;

@Service
@Getter
@NoArgsConstructor
public final class RedisOps {
    @Resource
    private RedisTemplate<Serializable, Object> redisTemplate;

    private ValueOperations<Serializable, Object> valueOperations;
    private HashOperations<Serializable, Serializable, Object> hashOperations;
    private ListOperations<Serializable, Object> listOperations;
    private SetOperations<Serializable, Object> setOperations;
    private ZSetOperations<Serializable, Object> zSetOperations;
    private GeoOperations<Serializable, Object> geoOperations;

    public RedisOps(RedisTemplate<Serializable, Object> redisTemplate) {
        this.valueOperations = redisTemplate.opsForValue();
        this.hashOperations = redisTemplate.opsForHash();
        this.listOperations = redisTemplate.opsForList();
        this.setOperations = redisTemplate.opsForSet();
        this.zSetOperations = redisTemplate.opsForZSet();
    }

    @SneakyThrows
    public boolean expire(String key, long time) {
        Assert.hasText(key, "key must not be null");
        Assert.isTrue(time > 0, "time must be greator than 0");
        return redisTemplate.expire(key, time, TimeUnit.MILLISECONDS);
    }

    @SneakyThrows
    public <T> void set(String key, T t) {
        Assert.hasText(key, "not be null");
        Assert.isTrue(t != null, "val must not be null");
        valueOperations.set(key, t);
    }

    @SneakyThrows
    public Object get(String key) {
        Assert.hasText(key, "not null");
        return valueOperations.get(key);
    }
}
