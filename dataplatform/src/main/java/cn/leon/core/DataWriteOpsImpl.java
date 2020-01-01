package cn.leon.core;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.ImmutableMap;
import com.sixi.micro.common.dto.PageData;
import com.sixi.search.coreservice.constant.ConfigConstant;
import com.sixi.search.coreservice.domain.entity.BaseDto;
import com.sixi.search.coreservice.domain.entity.MongoDto;
import com.sixi.search.coreservice.domain.form.EsStorageForm;
import com.sixi.search.coreservice.domain.form.IndexForm;
import com.sixi.search.coreservice.domain.form.SearchForm;
import com.sixi.search.coreservice.domain.form.StorageForm;
import com.sixi.search.coreservice.domain.form.StorageListForm;
import com.sixi.search.coreservice.domain.form.UpdateIndexForm;
import com.sixi.search.coreservice.kits.Elastickit;
import com.sixi.search.coreservice.kits.MongoKit;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

/**
 * @author mujian
 * @Desc
 * @date 2019/7/4 17:14
 */
@Service
@Slf4j
public class DataWriteOpsImpl implements DataWriteOps {

    @Autowired
    private MongoKit mongoKit;
    @Autowired
    private Elastickit elastickit;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveData(StorageListForm form) {
        List<MongoDto> mongoDtoList = form.getBaseDtoList().stream().map(baseDto -> {
            return MongoDto.builder().detail(baseDto.getDetail()).build();
        }).collect(Collectors.toList());
        List<String> ids = mongoKit.saveData(mongoDtoList, form.getBizName());
        // 1.索引
        elastickit.createIndex(IndexForm.builder().indices(form.getBizName())
                                        .replicas(form.getReplicate())
                                        .shard(form.getShard())
                                        .mapping(form.getBaseDtoList().get(0).getCondition())
                                        .build());
        Iterator<String> it = ids.iterator();
        List<EsStorageForm> list = form.getBaseDtoList().stream()
                                       .map(baseDto -> {
                                           baseDto.getCondition().put(ConfigConstant.STORAGEID, it.next());
                                           return EsStorageForm.builder().bizKey(baseDto.getBizKey())
                                                               .condition(baseDto.getCondition())
                                                               .opsDate(form.getOpsTime())
                                                               .bizName(form.getBizName()).build();
                                       }).collect(Collectors.toList());
        elastickit.insertDocuments(list);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int saveData(StorageForm form) {
        val id = this.saveDetail(form);
        // 1.索引
        elastickit.createIndex(IndexForm.builder().indices(form.getBizName())
                                        .replicas(form.getReplicate())
                                        .shard(form.getShard())
                                        .mapping(form.getBaseDto().getCondition())
                                        .build());
        BaseDto baseDto = form.getBaseDto();
        baseDto.getCondition().put(ConfigConstant.STORAGEID, id);
        return elastickit.insertDocument(EsStorageForm.builder()
                                                      .storageId(id)
                                                      .bizKey(baseDto.getBizKey())
                                                      .bizName(form.getBizName())
                                                      .condition(baseDto.getCondition())
                                                      .opsDate(form.getOpsTime())
                                                      .build());
    }

    @Override
    public int saveIndex(StorageForm form) {
        // 1.索引
        elastickit.createIndex(IndexForm.builder().indices(form.getBizName())
                                        .replicas(form.getReplicate())
                                        .shard(form.getShard())
                                        .mapping(form.getBaseDto().getCondition())
                                        .build());
        SearchForm searchForm = SearchForm.builder()
                                          .exact(ImmutableMap.<String, Object>builder().put("bizKey", form.getBaseDto().getCondition().get("bizKey"))
                                                                                       .build())
                                          .bizName(form.getBizName())
                                          .build();
        String storageId = elastickit.searchDocumentId(searchForm);
        BaseDto baseDto = form.getBaseDto();
        EsStorageForm esStorageForm = EsStorageForm.builder()
                                                   .bizKey(baseDto.getBizKey())
                                                   .bizName(form.getBizName())
                                                   .condition(baseDto.getCondition())
                                                   .opsDate(form.getOpsTime())
                                                   .build();
        if (StringUtils.isBlank(storageId)) {
            return elastickit.insert(esStorageForm);
        } else {
            esStorageForm.setStorageId(storageId);
            return elastickit.insertDocument(esStorageForm);
        }

    }

    @Override
    public String saveDetail(StorageForm form) {
        MongoDto mongoDto = MongoDto.builder()
                                    .detail(form.getBaseDto().getDetail())
                                    .bizId(form.getBaseDto().getBizKey())
                                    .build();
        if (StringUtils.isNotBlank(form.getStorageId())) {
            mongoDto.setId(form.getStorageId());
        }
        return mongoKit.saveData(mongoDto, form.getBizName());
    }

    @Override
    public int updateIndex(UpdateIndexForm form) {
        SearchForm searchForm = SearchForm.builder().build();
        searchForm.setBizName(form.getBizName());
        searchForm.setExact(ImmutableMap.<String, Object>builder()
                                    .put("bizKey", form.getBizKey())
                                    .build());
        PageData<Map<String, Object>> pageData = elastickit.searchDocument(searchForm);
        List<Map<String, Object>> list = pageData.getList();
        if (MapUtils.isEmpty(list.get(0))) {
            return 0;
        }
        Map<String, Object> condition = list.get(0);
        condition.putAll(form.getBaseDto().getCondition());
        String storageId = (String) condition.get("storageId");
        return elastickit.insertDocument(EsStorageForm.builder()
                                                      .condition(condition)
                                                      .storageId(storageId)
                                                      .bizName(form.getBizName())
                                                      .build());
    }
}
