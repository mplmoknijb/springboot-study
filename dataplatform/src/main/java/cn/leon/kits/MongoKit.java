package cn.leon.kits;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import cn.leon.constant.ConfigConstant;
import cn.leon.domain.form.MongoDto;
import cn.leon.domain.form.SearchForm;
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
        Query query = new Query(Criteria.where(ConfigConstant.BIZKEY).is(form.getBizKey()));
        // 排序模板
        //        Sort sort = new Sort(Sort.Direction.DESC, "age");
        //        PageRequest pageRequest = PageRequest.of(form.getPage(), form.getSize());
        return mongoTemplate.find(query, MongoDto.class, form.getBizName())
                            .stream().map(mongoDto -> {
                    return SearchVo.builder()
                                   .bizKey(mongoDto.getBizkey())
                                   .detail(mongoDto.getDetail())
                                   .opsTime(mongoDto.getOpsDate())
                                   .build();
                }).collect(Collectors.toList());
    }

    public SearchVo getOneById(SearchForm form) {
        Query query = new Query(Criteria.where(ConfigConstant.BIZKEY).is(form.getBizKey()));
        return mongoTemplate.findOne(query, SearchVo.class);
    }


    /**
     * 保存数据
     *
     * @param mongoDtoList
     */
    public void saveData(List<MongoDto> mongoDtoList, String bizName) {
        BulkOperations bulkOperations = mongoTemplate.bulkOps(BulkOperations.BulkMode.UNORDERED, MongoDto.class, bizName);
        bulkOperations.insert(mongoDtoList).execute();
        //        mongoTemplate.insert(mongoDtoList, form.getBizName());
    }

}
