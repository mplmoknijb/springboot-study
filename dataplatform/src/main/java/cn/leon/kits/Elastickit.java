package cn.leon.kits;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.hbase.thirdparty.com.google.common.collect.Lists;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.ShardSearchFailure;
import org.elasticsearch.action.support.ActiveShardCount;
import org.elasticsearch.action.support.WriteRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.support.replication.ReplicationResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Component;

import cn.leon.domain.form.EsStorageForm;
import cn.leon.domain.form.IndexForm;
import cn.leon.domain.form.SearchForm;
import cn.leon.domain.vo.SearchVo;
import cn.leon.util.ElasticSearchPoolUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * @author mujian
 * @Desc
 * @date 2019/7/5 9:20
 */
@Component
@Slf4j
public class Elastickit {

    @SneakyThrows(Exception.class)
    public boolean createIndex(IndexForm form) {
        RestHighLevelClient client = null;
        try {
            client = ElasticSearchPoolUtil.getClient();
            GetIndexRequest request = new GetIndexRequest(form.getIndices());
            boolean exists = client.indices().exists(request, RequestOptions.DEFAULT);
            if (exists) {
                return false;
            }
            CreateIndexRequest indexRequest = new CreateIndexRequest(form.getIndices());
            indexRequest.settings(Settings.builder().put("index.number_of_shards", form.getShard())
                                          .put("index.number_of_replicas", form.getReplicas()) // 副本数
                                          .put("analysis.analyzer.default.tokenizer", "standard")
            );
            //            XContentBuilder xContentBuilder = jsonBuilder()
            //                    //开启倒计时功能
            //                    .startObject("_ttl")
            //                    .field("enabled", false)
            //                    .endObject()
            //                    .startObject("properties")
            //                    .startObject("title")
            //                    .field("type", "string").endObject();
            //            indexRequest.mapping(xContentBuilder);
            //            request.alias(new Alias("lab1"));
            CreateIndexResponse response = client.indices().create(indexRequest, RequestOptions.DEFAULT);
            boolean acknowledged = response.isAcknowledged();
            boolean shardsAcknowledged = response.isShardsAcknowledged();
            return acknowledged && shardsAcknowledged;
        } finally {
            ElasticSearchPoolUtil.returnClient(client);
        }
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
            boolean exists = client.indices().exists(request, RequestOptions.DEFAULT);
        } finally {
            ElasticSearchPoolUtil.returnClient(client);
        }
        return false;
    }

    @SneakyThrows(Exception.class)
    public void insertDocument(EsStorageForm form) {
        RestHighLevelClient client = null;
        try {
            client = ElasticSearchPoolUtil.getClient();
            // 方式二：以map对象来表示文档
            IndexRequest request = new IndexRequest(
                    form.getBizName(),   //索引
                    "_doc",     // mapping type
                    form.getBizKey());     //文档id
            request.source(form.getCondition());
            //4、发送请求
            IndexResponse indexResponse = null;
            try {
                // 同步方式
                indexResponse = client.index(request, RequestOptions.DEFAULT);
            } catch (ElasticsearchException e) {
                // 捕获，并处理异常
                //判断是否版本冲突、create但文档已存在冲突
                if (e.status() == RestStatus.CONFLICT) {
                    log.error("冲突了，请在此写冲突处理逻辑！\n" + e.getDetailedMessage());
                }

                log.error("索引异常", e);
            }

            //5、处理响应
            if (indexResponse != null) {
                String index = indexResponse.getIndex();
                String type = indexResponse.getType();
                String id = indexResponse.getId();
                long version = indexResponse.getVersion();
                if (indexResponse.getResult() == DocWriteResponse.Result.CREATED) {
                    System.out.println("新增文档成功!");
                } else if (indexResponse.getResult() == DocWriteResponse.Result.UPDATED) {
                    System.out.println("修改文档成功!");
                }
                // 分片处理信息
                ReplicationResponse.ShardInfo shardInfo = indexResponse.getShardInfo();
                if (shardInfo.getTotal() != shardInfo.getSuccessful()) {

                }
                // 如果有分片副本失败，可以获得失败原因信息
                if (shardInfo.getFailed() > 0) {
                    for (ReplicationResponse.ShardInfo.Failure failure : shardInfo.getFailures()) {
                        String reason = failure.reason();
                        System.out.println("副本失败原因：" + reason);
                    }
                }
            }
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
                log.error("============尝试操作{}条数据============", bulkRequest.numberOfActions());
            }

            @Override
            public void afterBulk(long l, BulkRequest bulkRequest, BulkResponse bulkResponse) {
                log.error("============尝试操作{}条数据成功============", bulkRequest.numberOfActions());
            }

            @Override
            public void afterBulk(long l, BulkRequest bulkRequest, Throwable throwable) {
                log.error("============尝试操作{}条数据失败============", bulkRequest.numberOfActions());
            }
        };
        //        BiConsumer<BulkRequest, ActionListener<BulkResponse>> bulkConsumer =
        //                (request, bulkListener) ->
        //                        client.bulkAsync(request, RequestOptions.DEFAULT, bulkListener);
        //        BulkProcessor bulkProcessor = BulkProcessor.builder(bulkConsumer, listener)
        //                                                   // 1w次请求执行一次bulk
        //                                                   .setBulkActions(10000)
        //                                                   // 1gb的数据刷新一次bulk
        //                                                   .setBulkSize(new ByteSizeValue(1, ByteSizeUnit.GB))
        //                                                   // 固定5s必须刷新一次
        //                                                   .setFlushInterval(TimeValue.timeValueSeconds(5))
        //                                                   // 并发请求数量, 0不并发, 1并发允许执行
        //                                                   .setConcurrentRequests(1)
        //                                                   // 设置退避, 100ms后执行, 最大请求3次
        //                                                   .setBackoffPolicy(
        //                                                           BackoffPolicy.exponentialBackoff(TimeValue.timeValueMillis(100), 3))
        //                                                   .build();
        BulkRequest request = new BulkRequest();
        // 添加请求数据
        list.forEach(esStorageForm -> {
            //            bulkProcessor.add(new IndexRequest(esStorageForm.getBizName(), "_doc", esStorageForm.getBizKey()).source(esStorageForm.getCondition()));
            request.add(new IndexRequest(esStorageForm.getBizName(), "doc", esStorageForm.getBizKey()).source(esStorageForm.getCondition()));
        });
        request.timeout(TimeValue.timeValueMinutes(2));
        request.timeout("2m");
        request.setRefreshPolicy(WriteRequest.RefreshPolicy.WAIT_UNTIL);
        request.setRefreshPolicy("wait_for");
        request.waitForActiveShards(2);
        request.waitForActiveShards(ActiveShardCount.ALL);

        BulkResponse bulkResponse = client.bulk(request, RequestOptions.DEFAULT);
        //        bulkProcessor.flush();
        //        bulkProcessor.add(new DeleteRequest("testblog", "test", "2"));
        // 关闭
        //        bulkProcessor.awaitClose(30L, TimeUnit.SECONDS);
    }

    @SneakyThrows(Exception.class)
    public List<SearchVo> searchDocument(SearchForm form) {
        RestHighLevelClient client = ElasticSearchPoolUtil.getClient();
        // 1、创建search请求
        //SearchRequest searchRequest = new SearchRequest();
        SearchRequest searchRequest = new SearchRequest(form.getBizName());
        searchRequest.types("_doc");
        // 2、用SearchSourceBuilder来构造查询请求体 ,请仔细查看它的方法，构造各种查询的方法都在这。
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        //构造QueryBuilder
            /*QueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("user", "kimchy")
                    .fuzziness(Fuzziness.AUTO)
                    .prefixLength(3)
                    .maxExpansions(10);
            sourceBuilder.query(matchQueryBuilder);*/
        Map<String, String> map = form.getCondition();
        QueryBuilder matchQueryBuilder = QueryBuilders.moreLikeThisQuery(map.keySet().toArray(new String[0]),
                                                                         map.values().toArray(new String[0]),
                                                                         null);
        sourceBuilder.query(QueryBuilders.boolQuery().must(matchQueryBuilder));
        sourceBuilder.from(0);
        sourceBuilder.size(10);
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        //是否返回_source字段
        //sourceBuilder.fetchSource(false);

        //设置返回哪些字段
            /*String[] includeFields = new String[] {"title", "user", "innerObject.*"};
            String[] excludeFields = new String[] {"_type"};
            sourceBuilder.fetchSource(includeFields, excludeFields);*/

        //指定排序
        //sourceBuilder.sort(new ScoreSortBuilder().order(SortOrder.DESC));
        //sourceBuilder.sort(new FieldSortBuilder("_uid").order(SortOrder.ASC));

        // 设置返回 profile
        //sourceBuilder.profile(true);

        //将请求体加入到请求中
        searchRequest.source(sourceBuilder);

        // 可选的设置
        //searchRequest.routing("routing");

        // 高亮设置
            /*
            HighlightBuilder highlightBuilder = new HighlightBuilder();
            HighlightBuilder.Field highlightTitle =
                    new HighlightBuilder.Field("title");
            highlightTitle.highlighterType("unified");
            highlightBuilder.field(highlightTitle);
            HighlightBuilder.Field highlightUser = new HighlightBuilder.Field("user");
            highlightBuilder.field(highlightUser);
            sourceBuilder.highlighter(highlightBuilder);*/


        //加入聚合
            /*TermsAggregationBuilder aggregation = AggregationBuilders.terms("by_company")
                    .field("company.keyword");
            aggregation.subAggregation(AggregationBuilders.avg("average_age")
                    .field("age"));
            sourceBuilder.aggregation(aggregation);*/

        //做查询建议
            /*SuggestionBuilder termSuggestionBuilder =
                    SuggestBuilders.termSuggestion("user").text("kmichy");
                SuggestBuilder suggestBuilder = new SuggestBuilder();
                suggestBuilder.addSuggestion("suggest_user", termSuggestionBuilder);
            sourceBuilder.suggest(suggestBuilder);*/

        //3、发送请求
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        //4、处理响应
        //搜索结果状态信息
        RestStatus status = searchResponse.status();
        TimeValue took = searchResponse.getTook();
        Boolean terminatedEarly = searchResponse.isTerminatedEarly();
        boolean timedOut = searchResponse.isTimedOut();

        //分片搜索情况
        int totalShards = searchResponse.getTotalShards();
        int successfulShards = searchResponse.getSuccessfulShards();
        int failedShards = searchResponse.getFailedShards();
        for (ShardSearchFailure failure : searchResponse.getShardFailures()) {
            // failures should be handled here
        }
        List<SearchVo> reponseList = Lists.newArrayList();
        //处理搜索命中文档结果
        SearchHits hits = searchResponse.getHits();

        long totalHits = hits.getTotalHits();
        float maxScore = hits.getMaxScore();

        SearchHit[] searchHits = hits.getHits();
        for (SearchHit hit : searchHits) {
            // do something with the SearchHit

            String index = hit.getIndex();
            String type = hit.getType();
            String id = hit.getId();
            float score = hit.getScore();
            reponseList.add(SearchVo.builder().bizKey(id).build());
            //取_source字段值
            String sourceAsString = hit.getSourceAsString(); //取成json串
            Map<String, Object> sourceAsMap = hit.getSourceAsMap(); // 取成map对象
            //从map中取字段值
                /*
                String documentTitle = (String) sourceAsMap.get("title");
                List<Object> users = (List<Object>) sourceAsMap.get("user");
                Map<String, Object> innerObject = (Map<String, Object>) sourceAsMap.get("innerObject");
                */
            log.info("index:" + index + "  type:" + type + "  id:" + id);
            log.info(sourceAsString);
            //取高亮结果
                /*Map<String, HighlightField> highlightFields = hit.getHighlightFields();
                HighlightField highlight = highlightFields.get("title");
                Text[] fragments = highlight.fragments();
                String fragmentString = fragments[0].string();*/
        }

        // 获取聚合结果
            /*
            Aggregations aggregations = searchResponse.getAggregations();
            Terms byCompanyAggregation = aggregations.get("by_company");
            Bucket elasticBucket = byCompanyAggregation.getBucketByKey("Elastic");
            Avg averageAge = elasticBucket.getAggregations().get("average_age");
            double avg = averageAge.getValue();
            */

        // 获取建议结果
            /*Suggest suggest = searchResponse.getSuggest();
            TermSuggestion termSuggestion = suggest.getSuggestion("suggest_user");
            for (TermSuggestion.Entry entry : termSuggestion.getEntries()) {
                for (TermSuggestion.Entry.Option option : entry) {
                    String suggestText = option.getText().string();
                }
            }
            */

        //异步方式发送获查询请求
            /*
            ActionListener<SearchResponse> listener = new ActionListener<SearchResponse>() {
                @Override
                public void onResponse(SearchResponse getResponse) {
                    //结果获取
                }

                @Override
                public void onFailure(Exception e) {
                    //失败处理
                }
            };
            client.searchAsync(searchRequest, listener);
            */
        ElasticSearchPoolUtil.returnClient(client);
        return reponseList;
    }
}
