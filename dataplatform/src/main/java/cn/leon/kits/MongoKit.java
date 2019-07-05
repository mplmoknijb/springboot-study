package cn.leon.kits;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import cn.leon.domain.form.MongoDto;
import cn.leon.domain.form.SearchForm;
import cn.leon.domain.form.StorageForm;
import cn.leon.domain.vo.SearchVo;

@Component
public class MongoKit {

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 按条件分页查询
     *
     * @return
     */
    public List<SearchVo> getData(SearchForm form) {
        Query query = new Query(Criteria.where("_id").is(form.getBizKey()));
        // 排序模板
        //        Sort sort = new Sort(Sort.Direction.DESC, "age");
        //        PageRequest pageRequest = PageRequest.of(form.getPage(), form.getSize());
        return mongoTemplate.find(query, SearchVo.class);
    }

    public SearchVo getOneById(SearchForm form) {
        Query query = new Query(Criteria.where("_id").is(form.getBizKey()));
        return mongoTemplate.findOne(query, SearchVo.class);
    }


    /**
     * 保存数据
     *
     * @param form
     */
    public void saveData(StorageForm form) {
        List<MongoDto> mongoDtoList = form.getBaseEntityList().stream().map(baseEntity -> {
            return MongoDto.builder().id(baseEntity.getBizKey())
                           .opsDate(form.getOpsTime())
                           .userId(form.getUserId())
                           .detail(baseEntity.getDetail()).build();
        }).collect(Collectors.toList());
        //        BulkOperations bulkOperations = mongoTemplate.bulkOps(BulkOperations.BulkMode.UNORDERED, MongoDto.class, form.getBizName());
        //        bulkOperations.insert(mongoDtoList).execute();
        mongoTemplate.insert(mongoDtoList, form.getBizName());
    }

}
