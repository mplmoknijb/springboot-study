package cn.leon.config;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.interceptor.KeyGenerator;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * 生成自定义hashKey
 */
public class SelfKeyGen implements KeyGenerator {
    private static final Logger LOGGER = LoggerFactory.getLogger(SelfKeyGen.class);

    public static final int NO_PARAM_KEY = 0;
    public static final int NULL_PARAM_KEY = 53;

    @Override
    public Object generate(Object o, Method method, Object... objects) {
        StringBuilder key = new StringBuilder();
        Cacheable cacheable = method.getAnnotation(Cacheable.class);
        if (null != cacheable && StringUtils.isNotBlank(cacheable.value().toString())) {
            key.append(cacheable.value());
        }
        LOGGER.info("Cache Key cacheable'value: {}", cacheable.value()[0]);
        switch (objects.length) {
            case 0:
                key.append(NO_PARAM_KEY);
                LOGGER.info("params's length is 0 Cache Key: {}", key);
                break;
            case 1:
                this.addKeyWithOne(key, objects);
                break;
            default:
                /**
                 * deepHashCode可能导致同一key不同缓存
                 * 原因：生成hash key的方法为Arrays.deepHashCode看方法，没有对class类型的判断
                  */
                key.append(Arrays.deepHashCode(objects));
                LOGGER.info("deepHashCode(params) Cache Key: {}", key);
        }
        return key.toString();
    }

    private void addKeyWithOne(StringBuilder key, Object... objects) {
        Object param = objects[0];
        if (param == null) {
            key.append(NULL_PARAM_KEY);
            LOGGER.info("param is null Cache Key: {}", key);
        } else if (!param.getClass().isArray()) {
            key.append(param);
            LOGGER.info("param is not array Cache Key: {}", key);
        }
    }
}
