package cn.leon.util;

import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.elasticsearch.client.RestHighLevelClient;

import cn.leon.config.EsClientPoolFactory;

/**
 * @author mujian
 * @Desc
 * @date 2019/7/5 9:39
 */
public class ElasticSearchPoolUtil {
    private static GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();

    // 采用默认配置maxTotal是8，池中有8个client
    {
        poolConfig.setMaxTotal(3);
    }

    // 要池化的对象的工厂类，这个是我们要实现的类
    private static EsClientPoolFactory esClientPoolFactory = new EsClientPoolFactory();
    // 利用对象工厂类和配置类生成对象池
    private static GenericObjectPool<RestHighLevelClient> clientPool = new GenericObjectPool<>(esClientPoolFactory,
                                                                                               poolConfig);

    /**
     * 获得对象
     *
     * @return
     * @throws Exception
     */
    public static RestHighLevelClient getClient() throws Exception {
        // 从池中取一个对象
        RestHighLevelClient client = clientPool.borrowObject();
        return client;
    }

    /**
     * 释放
     *
     * @param client
     */
    public static void returnClient(RestHighLevelClient client) {
        clientPool.returnObject(client);
    }
}
