package cn.leon.config;

import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author mujian
 * @Desc
 * @date 2019/8/8 15:32
 */
@Configuration
public class ElasticConfig {

    @Bean
    public EsClientPoolFactory getEsClientPoolFactory(ElasticProperties elasticProperties) {
        return new EsClientPoolFactory(elasticProperties);
    }

    @Bean
    public GenericObjectPool getGenericObjectPool(EsClientPoolFactory esClientPoolFactory,
                                                  GenericObjectPoolConfig poolConfig,
                                                  ElasticProperties elasticProperties) {
        poolConfig.setMaxTotal(Integer.valueOf(elasticProperties.getMaxTotal()));
        return new GenericObjectPool(esClientPoolFactory, poolConfig);
    }

    @Bean
    public GenericObjectPoolConfig getGenericObjectPoolConfig() {
        return new GenericObjectPoolConfig();
    }
}
