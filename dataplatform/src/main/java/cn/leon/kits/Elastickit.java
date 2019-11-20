package cn.leon.kits;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;

import org.apache.hbase.thirdparty.com.google.common.collect.Lists;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BackoffPolicy;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import cn.leon.constant.ConfigConstant;
import cn.leon.domain.form.EsStorageForm;
import cn.leon.domain.form.IndexForm;
import cn.leon.domain.form.SearchForm;
import cn.leon.domain.vo.SearchVo;
import cn.leon.util.ElasticSearchPoolUtil;
import cn.leon.util.ParseUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * @author mujian
 * @Desc
 * @date 2019/7/5 9:20
 */
@Configuration
@DependsOn("redisDistributedKit")
@Slf4j
public class Elastickit {

    @Autowired
    private ParseUtil parseUtil;

    @SneakyThrows(Exception.class)
    public boolean createIndex(IndexForm form) {
        RestHighLevelClient client = null;
        //            // 设置别名
        //            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //            String dt = sdf.format(new Date());
        try {
            client = ElasticSearchPoolUtil.getClient();
            GetIndexRequest request = new GetIndexRequest(form.getIndices());
            boolean exist = client.indices().exists(request, RequestOptions.DEFAULT);
            if (exist) {
                return false;
            }
        } finally {
            ElasticSearchPoolUtil.returnClient(client);
        }
        CreateIndexRequest indexRequest = new CreateIndexRequest(form.getIndices());
        XContentBuilder builder = XContentFactory.jsonBuilder();
        builder.startObject();
        {
            builder.startObject("properties");
            {
                for (Map.Entry<String, Object> entry : form.getMapping().entrySet()) {
                    builder.startObject(entry.getKey());
                    {
                        builder.field("type", parseUtil.parseType(entry.getValue().getClass().getTypeName()));
                    }
                    builder.endObject();
                }
                builder.endObject();
            }
        }
        builder.endObject();
        indexRequest.settings(Settings.builder().put("index.number_of_shards", form.getShard())
                                      .put("index.number_of_replicas", form.getReplicas())
                                      .put("analysis.analyzer.default.tokenizer","standard"))
                    .mapping(builder);
        CreateIndexResponse response = client.indices().create(indexRequest, RequestOptions.DEFAULT);
        return response.isAcknowledged() && response.isShardsAcknowledged();

    }

    @SneakyThrows({Exception.class})
    public boolean dropIndex(IndexForm form) {
        RestHighLevelClient client = null;
        try {
            client = ElasticSearchPoolUtil.getClient();
            DeleteIndexRequest request = new DeleteIndexRequest(form.getIndices());
            AcknowledgedResponse acknowledgedResponse = client.indices().delete(request, RequestOptions.DEFAULT);
            return acknowledgedResponse.isAcknowledged();
        } finally {
            client.close();
        }
    }


    @SneakyThrows({Exception.class})
    public boolean checkIndex(IndexForm form) {
        RestHighLevelClient client = null;
        try {
            client = ElasticSearchPoolUtil.getClient();
            GetIndexRequest request = new GetIndexRequest(form.getIndices());
            return client.indices().exists(request, RequestOptions.DEFAULT);
        } finally {
            ElasticSearchPoolUtil.returnClient(client);
        }
    }

    @SneakyThrows(Exception.class)
    public int insertDocument(EsStorageForm esStorageForm) {
        RestHighLevelClient client = null;
        try {
            client = ElasticSearchPoolUtil.getClient();
            IndexRequest request = new IndexRequest(esStorageForm.getBizName())
                    .source(esStorageForm.getCondition());
            IndexResponse indexResponse = client.index(request, RequestOptions.DEFAULT);
            log.info("=================写入ElasticSearch:{}=====================", indexResponse.status().getStatus());
            return indexResponse.status().getStatus();
        } finally {
            ElasticSearchPoolUtil.returnClient(client);
        }
    }

    @SneakyThrows(Exception.class)
    public void insertDocuments(List<EsStorageForm> list) {
        RestHighLevelClient client = ElasticSearchPoolUtil.getClient();
        BulkProcessor.Listener listener = new BulkProcessor.Listener() {
            @Override
            public void beforeBulk(long l, BulkRequest bulkRequest) {
                log.info("============尝试操作{}条数据============", bulkRequest.numberOfActions());
            }

            @Override
            public void afterBulk(long l, BulkRequest bulkRequest, BulkResponse bulkResponse) {
                log.info("============尝试操作{}条数据成功============", bulkRequest.numberOfActions());
            }

            @Override
            public void afterBulk(long l, BulkRequest bulkRequest, Throwable throwable) {
                log.info("============尝试操作{}条数据失败============", bulkRequest.numberOfActions());
            }
        };
        BiConsumer<BulkRequest, ActionListener<BulkResponse>> bulkConsumer =
                (request, bulkListener) ->
                        client.bulkAsync(request, RequestOptions.DEFAULT, bulkListener);
        BulkProcessor bulkProcessor = BulkProcessor.builder(bulkConsumer, listener)
                                                   // 1w次请求执行一次bulk
                                                   .setBulkActions(10000)
                                                   // 1gb的数据刷新一次bulk
                                                   .setBulkSize(new ByteSizeValue(1, ByteSizeUnit.GB))
                                                   // 固定5s必须刷新一次
                                                   .setFlushInterval(TimeValue.timeValueSeconds(5))
                                                   // 并发请求数量, 0不并发, 1并发允许执行
                                                   .setConcurrentRequests(1)
                                                   // 设置退避, 100ms后执行, 最大请求3次
                                                   .setBackoffPolicy(BackoffPolicy.exponentialBackoff(TimeValue.timeValueMillis(100), 3))
                                                   .build();
        list.forEach(esStorageForm -> {
            bulkProcessor.add(new IndexRequest(esStorageForm.getBizName()).source(esStorageForm.getCondition()));
        });
        bulkProcessor.flush();
        // 关闭
        bulkProcessor.awaitClose(30L, TimeUnit.SECONDS);
        ElasticSearchPoolUtil.returnClient(client);
    }

    @SneakyThrows(Exception.class)
    public List<SearchVo> searchDocument(SearchForm form) {
        RestHighLevelClient client = ElasticSearchPoolUtil.getClient();
        //        final Scroll scroll = new Scroll(TimeValue.timeValueMinutes(1L));
        Map<String, Object> map = form.getCondition();
        List<String> reponseList = Lists.newArrayList();
        BoolQueryBuilder qb = QueryBuilders.boolQuery();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            qb.should(QueryBuilders.termQuery(entry.getKey(), entry.getValue()));
        }
//        if (form.getStartDt() == null || form.getEndDt() == null) {
//            return Collections.emptyList();
//        }
//        qb.must(QueryBuilders.rangeQuery("dateTime").from(ParseUtil.datePattern().format(form.getStartDt()))
//                             .to(ParseUtil.datePattern().format(form.getEndDt())));
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder()
                .query(qb)
                .from(form.getPage())
                .size(form.getNum())
                .timeout(new TimeValue(60, TimeUnit.SECONDS))
//                .sort(new FieldSortBuilder("dateTime").order(SortOrder.DESC))
                ;
        //        ActionListener<SearchResponse> listener = new ActionListener<SearchResponse>() {
        //            @Override
        //            public void onResponse(SearchResponse searchResponse) {
        //                //结果获取
        //                SearchHit[] hits = searchResponse.getHits().getHits();
        //                for (SearchHit hit : hits) {
        //                    Map<String, Object> sourceAsMap = hit.getSourceAsMap();
        //                    reponseList.add(sourceAsMap.get("id").toString());
        //                }
        //            }
        //
        //            @Override
        //            public void onFailure(Exception e) {
        //                //失败处理
        //                log.error("=================搜索失败:{}=================", e.getMessage());
        //            }
        //        };
        SearchResponse searchResponse = client.search(new SearchRequest()
                                                              .indices(form.getBizName())
                                                              .source(sourceBuilder), RequestOptions.DEFAULT);
        //        String scrollId = searchResponse.getScrollId();
        SearchHit[] hits = searchResponse.getHits().getHits();
        //        while (hits != null && hits.length > 0) {
        //            SearchScrollRequest scrollRequest = new SearchScrollRequest(scrollId);
        //            scrollRequest.scroll(scroll);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        List<SearchVo> resultList = Lists.newArrayList();
        for (SearchHit hit : hits) {
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            SearchVo searchVo = SearchVo.builder()
                                        .storageId(sourceAsMap.get(ConfigConstant.STORAGEID).toString())
                                        .dateTime(sourceAsMap.get("dateTime").toString().substring(0,10))
                                        .keywords(sourceAsMap.get("keywords").toString())
                                        .standardScore((Integer) sourceAsMap.get("standardScore"))
                                        .standardState((Integer) sourceAsMap.get("standardState"))
                                        .type((Integer) sourceAsMap.get("type"))
                                        .num((Integer) sourceAsMap.get("num"))
                                        .pageIndex((Integer) sourceAsMap.get("pageIndex"))
                                        .pageNum((Integer) sourceAsMap.get("pageNum"))
                                        .build();
            resultList.add(searchVo);
        }
        //            searchResponse = client.scroll(scrollRequest, RequestOptions.DEFAULT);
        //            scrollId = searchResponse.getScrollId();
        //            hits = searchResponse.getHits().getHits();
        //        }
        ElasticSearchPoolUtil.returnClient(client);
        return resultList;
    }

}
