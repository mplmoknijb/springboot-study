package cn.leon.kits;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.hbase.thirdparty.com.google.common.collect.Lists;
import org.apache.hbase.thirdparty.com.google.common.collect.Maps;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.admin.indices.alias.Alias;
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
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.CountRequest;
import org.elasticsearch.client.core.CountResponse;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.metrics.ParsedSum;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import com.sixi.micro.common.dto.PageData;
import com.sixi.micro.common.utils.Assert;
import com.sixi.search.coreservice.domain.form.AggregationForm;
import com.sixi.search.coreservice.domain.form.EsStorageForm;
import com.sixi.search.coreservice.domain.form.IndexForm;
import com.sixi.search.coreservice.domain.form.IndexInfoForm;
import com.sixi.search.coreservice.domain.form.SearchForm;
import com.sixi.search.coreservice.domain.vo.CountResultVo;
import com.sixi.search.coreservice.util.ConditionUtil;
import com.sixi.search.coreservice.util.ElasticSearchPoolUtil;
import com.sixi.search.coreservice.util.ParseUtil;
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
    private ElasticSearchPoolUtil elasticSearchPoolUtil;
    @Autowired
    private ParseUtil parseUtil;

    @Autowired
    private ConditionUtil conditionUtil;

    @SneakyThrows(Exception.class)
    public boolean createIndex(IndexForm form) {
        String index = form.getIndices().toLowerCase();
        RestHighLevelClient client = null;
        try {
            client = elasticSearchPoolUtil.getClient();
            GetIndexRequest request = new GetIndexRequest(index);
            boolean exist = client.indices().exists(request, RequestOptions.DEFAULT);
            if (exist) {
                return false;
            }
        } finally {
            elasticSearchPoolUtil.returnClient(client);
        }
        CreateIndexRequest indexRequest = new CreateIndexRequest(index).alias(new Alias(index.split("-")[0]));
        XContentBuilder builder = XContentFactory.jsonBuilder();
        builder.startObject();
        {
            builder.startObject("properties");
            {
                for (Map.Entry<String, Object> entry : form.getMapping().entrySet()) {
                    builder.startObject(entry.getKey());
                    {
                        String name = Objects.nonNull(entry.getValue()) ? entry.getValue().getClass().getTypeName() : "java.lang.String";
                        builder.field("type", parseUtil.parseType(name));
                        if (parseUtil.parseType(name).equals("text")) {
                            builder.field("analyzer", "ik_max_word");
                        }
                    }
                    builder.endObject();
                }
            }
            builder.endObject();
        }
        builder.endObject();
        indexRequest.settings(Settings.builder().put("index.number_of_shards", form.getShard())
                                      .put("index.number_of_replicas", form.getReplicas())).mapping(builder);
        CreateIndexResponse response = client.indices().create(indexRequest, RequestOptions.DEFAULT);
        return response.isAcknowledged() && response.isShardsAcknowledged();
    }

    @SneakyThrows({Exception.class})
    public boolean dropIndex(IndexForm form) {
        RestHighLevelClient client = null;
        try {
            client = elasticSearchPoolUtil.getClient();
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
            client = elasticSearchPoolUtil.getClient();
            GetIndexRequest request = new GetIndexRequest(form.getIndices());
            return client.indices().exists(request, RequestOptions.DEFAULT);
        } finally {
            elasticSearchPoolUtil.returnClient(client);
        }
    }


    @SneakyThrows(Exception.class)
    public int insertDocument(EsStorageForm esStorageForm) {
        RestHighLevelClient client = null;
        try {
            client = elasticSearchPoolUtil.getClient();
            Map<String, Object> condition = esStorageForm.getCondition();
            UpdateRequest updateRequest = new UpdateRequest(esStorageForm.getBizName(), esStorageForm.getStorageId())
                    .doc(condition)
                    .upsert(condition);
            UpdateResponse response = client.update(updateRequest, RequestOptions.DEFAULT);
            log.info("=================写入ElasticSearch:{}=====================", response.status().getStatus());
            return response.status().getStatus();
        } finally {
            elasticSearchPoolUtil.returnClient(client);
        }
    }

    @SneakyThrows(Exception.class)
    public int insert(EsStorageForm esStorageForm) {
        RestHighLevelClient client = null;
        try {
            client = elasticSearchPoolUtil.getClient();
            Map<String, Object> condition = esStorageForm.getCondition();
            IndexRequest indexRequest = new IndexRequest(esStorageForm.getBizName())
                    .source(condition);
            IndexResponse response = client.index(indexRequest, RequestOptions.DEFAULT);
            log.info("=================写入ElasticSearch:{}=====================", response.status().getStatus());
            return response.status().getStatus();
        } finally {
            elasticSearchPoolUtil.returnClient(client);
        }
    }

    @SneakyThrows(Exception.class)
    public void insertDocuments(List<EsStorageForm> list) {
        RestHighLevelClient client = elasticSearchPoolUtil.getClient();
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
        elasticSearchPoolUtil.returnClient(client);
    }

    @SneakyThrows(Exception.class)
    public PageData<Map<String, Object>> searchDocument(SearchForm form) {
        RestHighLevelClient client = elasticSearchPoolUtil.getClient();
        BoolQueryBuilder qb = searchAction(form);
        RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("opsDate");
        if (Objects.nonNull(form.getStartDt())) {
            rangeQueryBuilder.from(form.getStartDt());
        }
        if (Objects.nonNull(form.getStartDt())) {
            rangeQueryBuilder.to(form.getEndDt());
        }
        if (Objects.nonNull(form.getStartDt()) || Objects.nonNull(form.getEndDt())) {
            qb.must(rangeQueryBuilder);
        }
        int page = form.getPageNum();
        int size = form.getPageSize();
        int from = size * (page - 1);
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder()
                .query(qb)
                .from(from)
                .size(size)
                .timeout(new TimeValue(60, TimeUnit.SECONDS));
        // 暂不支持跨页
        //        Object[] objects = new Object[]{"start","start"};
        //        if(!objects[1].toString().equals("start") && !objects[1].toString().equals("start")) {
        //            sourceBuilder.searchAfter(objects);
        //        }
        getSortType(form.getSort(), sourceBuilder);
        //        final Scroll scroll = new Scroll(TimeValue.timeValueMinutes(1L));
        SearchRequest searchRequest = new SearchRequest().indices(form.getBizName().concat("-*")).source(sourceBuilder);
        //                .scroll(scroll);
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        //        String scrollId = searchResponse.getScrollId();
        SearchHit[] hits = searchResponse.getHits().getHits();
        List<Map<String, Object>> resultList = Lists.newArrayList();
        //        while (hits != null && hits.length > 0) {
        //            SearchScrollRequest scrollRequest = new SearchScrollRequest(scrollId);
        //            scrollRequest.scroll(scroll);
        //            searchResponse = client.scroll(scrollRequest, RequestOptions.DEFAULT);
        //        objects = hits[hits.length-1].getSortValues();
        for (SearchHit hit : hits) {
            resultList.add(hit.getSourceAsMap());
        }
        //            scrollId = searchResponse.getScrollId();
        //            hits = searchResponse.getHits().getHits();
        //        }
        elasticSearchPoolUtil.returnClient(client);
        PageData<Map<String, Object>> pageData = PageData.<Map<String, Object>>builder().size(form.getPageSize())
                                                                                        .num(form.getPageNum())
                                                                                        .list(resultList)
                                                                                        .count(searchResponse.getHits().getTotalHits().value)
                                                                                        .build();
        return pageData;
    }

    @SneakyThrows(Exception.class)
    public Map<String, Double> aggregationCountIndexByCondition(AggregationForm form) {
        RestHighLevelClient client = elasticSearchPoolUtil.getClient();
        Map<String, Double> resultMap = Maps.newHashMap();
        BoolQueryBuilder qb = QueryBuilders.boolQuery();
        getExatCondition(form.getExact(), qb);
        getFuzzCondition(form.getFuzz(), qb);
        RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("opsDate");
        if (Objects.nonNull(form.getStartDt())) {
            rangeQueryBuilder.from(form.getStartDt());
        }
        if (Objects.nonNull(form.getStartDt())) {
            rangeQueryBuilder.to(form.getEndDt());
        }
        if (Objects.nonNull(form.getStartDt()) || Objects.nonNull(form.getEndDt())) {
            qb.must(rangeQueryBuilder);
        }
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder()
                .query(qb)
                .size(form.getPageSize())
                .timeout(new TimeValue(60, TimeUnit.SECONDS));
        Map<String, String> aggs = form.getAggs();
        if (MapUtils.isNotEmpty(aggs)) {
            for (Map.Entry<String, String> entry : aggs.entrySet()) {
                if (conditionUtil.isNull(entry)) {
                    continue;
                }
                sourceBuilder.aggregation(AggregationBuilders.sum(entry.getKey()).field(entry.getValue()));
            }
        }
        SearchRequest searchRequest = new SearchRequest().indices(form.getBizName().concat("-*")).source(sourceBuilder);
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        Aggregations aggregations = searchResponse.getAggregations();
        if (Objects.isNull(aggregations)) {
            return resultMap;
        }
        Map<String, Aggregation> stringAggregationMap = aggregations.asMap();
        aggs.forEach((key, val) -> {
            Aggregation agg = stringAggregationMap.get(key);
            resultMap.put(key, ((ParsedSum) agg).getValue());
        });
        elasticSearchPoolUtil.returnClient(client);
        return resultMap;
    }

    @SneakyThrows(Exception.class)
    public CountResultVo countIndexByCodition(SearchForm searchForm) {
        RestHighLevelClient client = elasticSearchPoolUtil.getClient();
        BoolQueryBuilder qb = QueryBuilders.boolQuery();
        getExatCondition(searchForm.getExact(), qb);
        getFuzzCondition(searchForm.getFuzz(), qb);
        getReverseCondition(searchForm.getReverse(), qb);
        getRangeCondition(searchForm.getRange(), qb);
        qb.minimumShouldMatch(searchForm.getShouldMatch());
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder().query(qb);
        CountRequest countRequest = new CountRequest().indices(searchForm.getBizName().concat("-*")).source(sourceBuilder);
        CountResponse countResponse = client.count(countRequest, RequestOptions.DEFAULT);
        elasticSearchPoolUtil.returnClient(client);
        return CountResultVo.builder().countValue(countResponse.getCount()).build();
    }

    @SneakyThrows(Exception.class)
    public String searchDocumentId(SearchForm form) {
        RestHighLevelClient client = elasticSearchPoolUtil.getClient();
        BoolQueryBuilder qb = searchAction(form);
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder()
                .query(qb)
                .timeout(new TimeValue(60, TimeUnit.SECONDS));
        SearchRequest searchRequest = new SearchRequest().indices(form.getBizName().toLowerCase().concat("-*")).source(sourceBuilder);
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        elasticSearchPoolUtil.returnClient(client);
        SearchHit[] hits = searchResponse.getHits().getHits();
        return hits.length == 0 ? "" : hits[0].getSourceAsMap().get("storageId").toString();
    }

    @SneakyThrows(Exception.class)
    public List<String> checkDateWithData(IndexInfoForm form) {
        RestHighLevelClient client = elasticSearchPoolUtil.getClient();
        List<String> resultLsit = Lists.newArrayList();
        Assert.forbidden(form.getDateList().isEmpty(), "日期列表不能为空");
        for (String index : form.getIndices()) {
            GetIndexRequest getIndexRequest = new GetIndexRequest(index);
            boolean exists = client.indices().exists(getIndexRequest, RequestOptions.DEFAULT);
            if (!exists) {
                return resultLsit;
            }
            List<String> dateList = form.getDateList();
            GetIndexRequest request = new GetIndexRequest(index);
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            BoolQueryBuilder qb = new BoolQueryBuilder();
            getExatCondition(form.getExcat(), qb);
            searchSourceBuilder.query(qb);
            GetIndexResponse getIndexResponse = client.indices().get(request, RequestOptions.DEFAULT);
            List<String> indices = Arrays.asList(getIndexResponse.getIndices());
            dateList.forEach(s -> {
                if (indices.contains(index.concat("-").concat(s))) {
                    CountRequest countRequest = new CountRequest(index.concat("-").concat(s));
                    countRequest.source(searchSourceBuilder);
                    try {
                        CountResponse countResponse = client.count(countRequest, RequestOptions.DEFAULT);
                        if (countResponse.getCount() > 0) {
                            resultLsit.add(s);
                        }
                    } catch (IOException e) {
                        log.error(e.getMessage());
                    }
                }
            });
        }
        elasticSearchPoolUtil.returnClient(client);
        return resultLsit;
    }

    private BoolQueryBuilder searchAction(SearchForm form) {
        BoolQueryBuilder qb = QueryBuilders.boolQuery();
        getExatCondition(form.getExact(), qb);
        getFuzzCondition(form.getFuzz(), qb);
        getRangeCondition(form.getRange(), qb);
        return qb;
    }

    private void getExatCondition(Map<String, Object> exact, BoolQueryBuilder qb) {
        if (MapUtils.isNotEmpty(exact)) {
            for (Map.Entry<String, Object> entry : exact.entrySet()) {
                if (conditionUtil.isNull(entry)) {
                    continue;
                }
                Object value = entry.getValue();
                String key = entry.getKey();
                if (value instanceof String) {
                    qb.must(QueryBuilders.matchPhraseQuery(key, value));
                } else if (value instanceof List) {
                    List<String> list = (List<String>) value;
                    String[] values = list.toArray(new String[]{});
                    qb.must(QueryBuilders.termsQuery(key, values));
                } else {
                    qb.must(QueryBuilders.termQuery(key, value));
                }

            }
        }
    }

    private void getFuzzCondition(Map<String, Object> fuzz, BoolQueryBuilder qb) {
        if (MapUtils.isNotEmpty(fuzz)) {
            for (Map.Entry<String, Object> entry : fuzz.entrySet()) {
                if (conditionUtil.isNull(entry)) {
                    continue;
                }
                qb.should(QueryBuilders.matchPhrasePrefixQuery(entry.getKey(), entry.getValue()));
            }
            qb.minimumShouldMatch(1);
        }
    }

    private void getReverseCondition(Map<String, Object> reverse, BoolQueryBuilder qb) {
        if (MapUtils.isNotEmpty(reverse)) {
            for (Map.Entry<String, Object> entry : reverse.entrySet()) {
                if (conditionUtil.isNull(entry)) {
                    continue;
                }
                qb.mustNot(QueryBuilders.matchPhraseQuery(entry.getKey(), entry.getValue()));
            }
        }
    }

    private void getRangeCondition(Map<String, Map<String, String>> range, BoolQueryBuilder qb) {
        if (MapUtils.isNotEmpty(range)) {
            for (Map.Entry<String, Map<String, String>> entry : range.entrySet()) {
                if (conditionUtil.isNull(entry)) {
                    continue;
                }
                RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery(entry.getKey());
                Map<String, String> stringMap = entry.getValue();
                stringMap.forEach((sign, v) -> {
                    if (StringUtils.isNotBlank(sign) && StringUtils.isNotBlank(v)) {
                        switch (sign) {
                            case "1":
                                rangeQueryBuilder.gt(v);
                                break;
                            case "2":
                                rangeQueryBuilder.gte(v);
                                break;
                            case "3":
                                rangeQueryBuilder.lt(v);
                                break;
                            case "4":
                                rangeQueryBuilder.lte(v);
                                break;
                        }
                    }
                });
                qb.must(rangeQueryBuilder);
            }
        }
    }

    private void getSortType(Map<String, String> sort, SearchSourceBuilder searchSourceBuilder) {
        if (MapUtils.isEmpty(sort)) {
            return;
        }
        for (Map.Entry<String, String> entry : sort.entrySet()) {
            searchSourceBuilder.sort(entry.getKey(), entry.getValue().equals("desc") ? SortOrder.DESC : SortOrder.ASC);
        }
    }
}
