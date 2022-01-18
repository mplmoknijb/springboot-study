package cn.leon.base;

import cn.leon.domain.form.IndexForm;
import cn.leon.util.ElasticSearchPoolUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.alias.Alias;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 * @author mujian
 * @Desc
 * @date 2019/7/3 15:09
 */
@Slf4j
public class EsIndexServiceImpl implements EsIndexService {
    /**
     * 共4种方式
     */
    @Override
    @SneakyThrows(Exception.class)
    public void createIndex(IndexForm form) {
        RestHighLevelClient client = null;
        try {
            client = ElasticSearchPoolUtil.getClient();
            CreateIndexRequest request = new CreateIndexRequest("book8");
            request.settings(Settings.builder().put("index.number_of_shards", 5)
                                     .put("index.number_of_replicas", 2) // 副本数
                                     .put("analysis.analyzer.default.tokenizer", "standard")
            );
            XContentBuilder xContentBuilder = jsonBuilder()
                    //开启倒计时功能
                    .startObject("_ttl")
                    .field("enabled", false)
                    .endObject()
                    .startObject("properties")
                    .startObject("title")
                    .field("type", "string").endObject();
            request.mapping(xContentBuilder);
            request.alias(new Alias("lab1"));
            CreateIndexResponse response = client.indices().create(request, RequestOptions.DEFAULT);
            boolean acknowledged = response.isAcknowledged();
            boolean shardsAcknowledged = response.isShardsAcknowledged();
        } finally {
            client.close();
        }
    }

    @Override
    @SneakyThrows({Exception.class})
    public boolean dropIndex(IndexForm form) {
        RestHighLevelClient client = null;
        try {
            client = ElasticSearchPoolUtil.getClient();
            DeleteIndexRequest request = new DeleteIndexRequest(form.getIndices());
            AcknowledgedResponse response = client.indices().delete(request, RequestOptions.DEFAULT);
            return response.isAcknowledged();
        } finally {
            client.close();
        }
    }

    @Override
    @SneakyThrows({Exception.class})
    public boolean checkIndex(IndexForm form) {
        RestHighLevelClient client = null;
        try {
            client = ElasticSearchPoolUtil.getClient();
            GetIndexRequest request = new GetIndexRequest(form.getIndices());
            boolean exists = client.indices().exists(request, RequestOptions.DEFAULT);
        } finally {
            client.close();
        }
        return false;
    }
}
