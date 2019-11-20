package cn.leon.config;

import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;

/**
 * @author mujian
 * @Desc 连接池工厂
 * @date 2019/7/3 14:43
 */
public class EsClientPoolFactory implements PooledObjectFactory<RestHighLevelClient> {

    @Override
    public PooledObject<RestHighLevelClient> makeObject() throws Exception {
        Settings settings = Settings.builder().put("cluster.name", "elasticsearch").build();
        RestHighLevelClient client = null;
        try {
			/*client = new PreBuiltTransportClient(settings)
                    .addTransportAddress(new TransportAddress(InetAddress.getByName("localhost"),9300));*/
            client = new RestHighLevelClient(RestClient.builder(
                    new HttpHost("172.16.200.38", 9200, "http")));
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
    public void activateObject(PooledObject<RestHighLevelClient> pooledObject) throws Exception {
    }

    @Override
    public void passivateObject(PooledObject<RestHighLevelClient> pooledObject) throws Exception {
    }
}
