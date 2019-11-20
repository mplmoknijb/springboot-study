package cn.leon.message;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.Message;

import cn.leon.constant.ConfigConstant;
import cn.leon.domain.form.BaseEntity;
import cn.leon.domain.form.StorageForm;
import cn.leon.domain.vo.RankingListVo;
import cn.leon.domain.vo.ReqDataVo;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;

/**
 * @author mujian
 * @Desc
 * @date 2019/7/9 18:01
 */
@EnableBinding(DataStreamInput.class)
@Slf4j
public class QueryResultListener {

    @Autowired
    private DataPlatFormOps dataPlatFormOps;
    private static AtomicInteger standardState = new AtomicInteger(0);
    private static AtomicInteger standardScore = new AtomicInteger(0);
    public static final String RESULTID = "ResultId";

    @Autowired
    private RedisTemplate redisTemplate;

    @StreamListener(DataStreamInput.QUERY_TASK_INPUT)
    public void resultListener(Message<String> message) {
        JSONObject context = JSONObject.parseObject(message.getPayload());
        String bizId = context.getString("bizId");
        //        boolean isMember = redisTemplate.opsForSet().isMember(RESULTID, bizId);
        boolean isMember = false;
        if (isMember || !context.getBoolean("success")) {
            return;
        }
        JSONObject content = context.getJSONObject("content");
        // 遍历 list
        List<RankingListVo> rankingListVoList = content.getJSONArray("list").toJavaList(RankingListVo.class);
        Map<String, RankingListVo> rankingListMap = rankingListVoList.stream()
                                                                     .collect(HashMap::new,
                                                                              (m, v) -> m.put(v.getItem().getData().getMemberId(), v),
                                                                              HashMap::putAll);
        // 公共条件
        Map<String, Object> condition = Maps.newHashMap();
        JSONObject reqData = context.getJSONObject("reqData");
        Integer size = reqData.getInteger("pageSize");
        condition.put("dateTime",reqData.getDate("dateTime"));
        condition.put(ConfigConstant.BIZKEY, bizId);
        condition.put("keywords", reqData.getString("keywords"));
        Map<String, ReqDataVo> reqDataVoMap = reqData.getJSONArray("selector").toJavaList(ReqDataVo.class)
                                                     .stream().collect(HashMap::new, (m, v) -> m.put(v.getMemberId(), v), HashMap::putAll);
        for (Map.Entry<String, RankingListVo> entry : rankingListMap.entrySet()) {
            int index2 = reqDataVoMap.get(entry.getKey()).getTarget2().getTargetPageindex();
            int index1 = reqDataVoMap.get(entry.getKey()).getTarget1().getTargetPageindex();
            int num1 = reqDataVoMap.get(entry.getKey()).getTarget1().getTargetPagenum();
            int num2 = reqDataVoMap.get(entry.getKey()).getTarget1().getTargetPagenum();
            int index = entry.getValue().getPageIndex();
            int num = entry.getValue().getPageNum();
            int rankingNum = (num - 1) * size + index;
            // 热力值
            if (rankingNum <= 100) {
                standardScore.set(101 - rankingNum);
            } else {
                standardScore.set(0);
            }
            // 未达标
            if (index > index1 || (index <= index1 && num > num1)) {
                standardState.set(0);
            }
            // 优秀
            else if (index <= index2 && num <= num2) {
                standardState.set(2);
            }
            // 达标
            else {
                standardState.set(1);
            }
            condition.put("standardScore", standardScore.get());
            condition.put("standardState", standardState.get());
            condition.put("pageSize", size);
            condition.put("pageIndex", index);
            condition.put("pageNum", num);
            condition.put("type", reqData.getInteger("type"));
            condition.put("num", rankingNum);
            condition.put(ConfigConstant.MEMBERID, entry.getKey());
            condition.put("shopName", reqDataVoMap.get(entry.getKey()).getShopName());
            Map<String, String> detailMap = Maps.newHashMap();
            detailMap.put(ConfigConstant.LIST, entry.getValue().toString());
            detailMap.put(ConfigConstant.ORIGINALLIST, content.getString("originalList"));
            BaseEntity baseEntity = BaseEntity.builder()
                                              .bizKey(bizId)
                                              .condition(condition)
                                              .detail(detailMap)
                                              .build();
            StorageForm storageForm = StorageForm.builder().replicate(1)
                                                 .shard(2)
                                                 .baseEntity(baseEntity)
                                                 .opsTime(new Date())
                                                 .bizName(context.getString("cmd").toLowerCase())
                                                 .build();
            int status = dataPlatFormOps.saveData(storageForm);
            //            if (status == 201) {
            //                redisTemplate.opsForSet().add(RESULTID, bizId);
            //                redisTemplate.opsForValue().set(RESULTID, bizId, 24 * 3600, TimeUnit.SECONDS);
            //            }
        }
    }
}
