package cn.leon.core;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.leon.domain.form.EsStorageForm;
import cn.leon.domain.form.IndexForm;
import cn.leon.domain.form.MongoDto;
import cn.leon.domain.form.SearchForm;
import cn.leon.domain.form.StorageForm;
import cn.leon.domain.vo.SearchVo;
import cn.leon.kits.Elastickit;
import cn.leon.kits.MongoKit;

/**
 * @author mujian
 * @Desc
 * @date 2019/7/4 17:14
 */
@Service
public class DataPlatFormOps implements DataOps {

    @Autowired
    private MongoKit mongoKit;
    @Autowired
    private Elastickit elastickit;

    @Override
    public void saveData(StorageForm form) {
        // 1.索引
        IndexForm indexForm = IndexForm.builder().indices(form.getBizName())
                                       .replicas(form.getReplicate())
                                       .shard(form.getShard()).build();
        boolean index = elastickit.createIndex(indexForm);
        if (!index) {
            return;
        }
        // 2.详情
        List<EsStorageForm> list = form.getBaseEntityList().stream()
                                       .map(baseEntity -> {
                                           return EsStorageForm.builder().bizKey(baseEntity.getBizKey())
                                                               .condition(baseEntity.getCondition())
                                                               .bizName(form.getBizName()).build();
                                       }).collect(Collectors.toList());
        List<String> ids = elastickit.insertDocuments(list);
        Iterator<String> id = ids.iterator();
        List<MongoDto> mongoDtoList = form.getBaseEntityList().stream().map(baseEntity -> {
            return MongoDto.builder().bizkey(baseEntity.getBizKey())
                           .id(id.next())
                           .opsDate(form.getOpsTime())
                           .userId(form.getUserId())
                           .detail(baseEntity.getDetail()).build();
        }).collect(Collectors.toList());
        mongoKit.saveData(mongoDtoList, form.getBizName());
    }

    @Override
    public List<SearchVo> getData(SearchForm form) {
        List<SearchVo> searchVos = elastickit.searchDocument(form);
        form.setBizKey(searchVos.get(0).getBizKey());
        return mongoKit.getData(form);
    }
}
