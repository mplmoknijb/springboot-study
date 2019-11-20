package cn.leon.config;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.google.common.collect.Lists;
import com.sixi.micro.common.utils.Assert;

/**
 * @author mujian
 * @Desc 连接池工厂
 * @date 2019/7/3 14:43
 */
@Configuration
@ConditionalOnClass({RestHighLevelClient.class, ElasticProperties.class})
@EnableConfigurationProperties(ElasticProperties.class)
public class EsClientPoolFactory implements PooledObjectFactory<RestHighLevelClient> {
    private ElasticProperties elasticProperties;

    public EsClientPoolFactory(ElasticProperties elasticProperties) {
        this.elasticProperties = elasticProperties;
    }

    @Override
    public PooledObject<RestHighLevelClient> makeObject() {
        RestHighLevelClient client = null;
        Assert.forbidden(StringUtils.isEmpty(elasticProperties.getHost()), "ElasticSearch host is null！");
        List<HttpHost> httpHosts = Lists.newArrayList();
        String[] urls = elasticProperties.getHost().split(",");
        for (String url : urls) {
            String[] uri = url.split(":");
            httpHosts.add(new HttpHost(uri[0], Integer.valueOf(uri[1]), "http"));
        }
        try {
            client = new RestHighLevelClient(RestClient.builder(httpHosts.toArray(new HttpHost[0])));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new DefaultPooledObject<RestHighLevelClient>(client);
    }

    @Override
    public void destroyObject(PooledObject<RestHighLevelClient> pooledObject) throws Exception {
        RestHighLevelClient highLevelClient = pooledObject.getObject();
        highLevelClient.close();
    }

    @Override
    public boolean validateObject(PooledObject<RestHighLevelClient> pooledObject) {
        return true;
    }

    @Override
    public void activateObject(PooledObject<RestHighLevelClient> pooledObject) {
    }

    @Override
    public void passivateObject(PooledObject<RestHighLevelClient> pooledObject) {
    }
}
