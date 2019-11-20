package cn.leon.core;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.leon.constant.ConfigConstant;
import cn.leon.domain.form.BaseEntity;
import cn.leon.domain.form.EsStorageForm;
import cn.leon.domain.form.IndexForm;
import cn.leon.domain.form.MongoDto;
import cn.leon.domain.form.SearchForm;
import cn.leon.domain.form.StorageForm;
import cn.leon.domain.form.StorageListForm;
import cn.leon.domain.vo.SearchVo;
import cn.leon.kits.Elastickit;
import cn.leon.kits.MongoKit;
import com.google.common.collect.Maps;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * @author mujian
 * @Desc
 * @date 2019/7/4 17:14
 */
@Service
@Slf4j
public class DataPlatFormOps implements DataOps {

    @Autowired
    private MongoKit mongoKit;
    @Autowired
    private Elastickit elastickit;

    @Override
    @Transactional(rollbackFor = Exception.class)
    @SneakyThrows(Exception.class)
    public void saveData(StorageListForm form) {
        List<MongoDto> mongoDtoList = form.getBaseEntityList().stream().map(baseEntity -> {
            return MongoDto.builder().detail(baseEntity.getDetail()).build();
        }).collect(Collectors.toList());
        List<String> ids = mongoKit.saveData(mongoDtoList, form.getBizName());
        // 1.索引
        elastickit.createIndex(IndexForm.builder().indices(form.getBizName())
                                        .replicas(form.getReplicate())
                                        .shard(form.getShard())
                                        .mapping(form.getBaseEntityList().get(0).getCondition())
                                        .build());
        Iterator<String> it = ids.iterator();
        List<EsStorageForm> list = form.getBaseEntityList().stream()
                                       .map(baseEntity -> {
                                           baseEntity.getCondition().put(ConfigConstant.STORAGEID, it.next());
                                           return EsStorageForm.builder().bizKey(baseEntity.getBizKey())
                                                               .condition(baseEntity.getCondition())
                                                               .bizName(form.getBizName()).build();
                                       }).collect(Collectors.toList());
        elastickit.insertDocuments(list);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @SneakyThrows(Exception.class)
    public int saveData(StorageForm form) {
        MongoDto mongoDto = MongoDto.builder().detail(form.getBaseEntity().getDetail())
                                    .bizId(form.getBaseEntity().getBizKey()).build();
        String id = mongoKit.saveData(mongoDto, form.getBizName());
        // 1.索引
        elastickit.createIndex(IndexForm.builder().indices(form.getBizName())
                                        .replicas(form.getReplicate())
                                        .shard(form.getShard())
                                        .mapping(form.getBaseEntity().getCondition())
                                        .build());
        BaseEntity baseEntity = form.getBaseEntity();
        baseEntity.getCondition().put(ConfigConstant.STORAGEID, id);
        return elastickit.insertDocument(EsStorageForm.builder()
                                                      .bizKey(baseEntity.getBizKey())
                                                      .bizName(form.getBizName())
                                                      .condition(baseEntity.getCondition())
                                                      .build());
    }


    @Override
    public List<SearchVo> getData(SearchForm form) {
        long esStart = System.currentTimeMillis();
        List<SearchVo> searchVos = elastickit.searchDocument(form);
        long esEnd = System.currentTimeMillis();
        log.info("===============es: {}================", esEnd - esStart);
//        form.setIdList(idList);
//        long mongoStart = System.currentTimeMillis();
//        List<SearchVo> list = mongoKit.getDatas(form);
//        long mongoEnd = System.currentTimeMillis();
//        log.info("===============mongo: {}================", mongoEnd - mongoStart);
        return searchVos;
    }



    @Override
    public SearchVo getDeatilById(SearchForm form) {
        return mongoKit.getData(form);
    }
}
