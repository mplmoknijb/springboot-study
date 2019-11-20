package cn.leon.kits;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import cn.leon.constant.ConfigConstant;
import cn.leon.domain.form.MongoDto;
import cn.leon.domain.form.SearchForm;
import cn.leon.domain.vo.SearchVo;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class MongoKit {

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 按条件分页查询
     *
     * @return
     */
    //    public List<SearchVo> getData(SearchForm form) {
    //        Query query = new Query(Criteria.where(ConfigConstant.BIZKEY).is(form.getBizKey()));
    //
    //        // 排序模板
    //        //        Sort sort = new Sort(Sort.Direction.DESC, "age");
    //        //        PageRequest pageRequest = PageRequest.of(form.getPage(), form.getSize());
    //        return mongoTemplate.find(query, MongoDto.class, form.getBizName())
    //                            .stream().map(mongoDto -> {
    //                    return SearchVo.builder()
    //                                   .detail(mongoDto.getDetail())
    //                                   .build();
    //                }).collect(Collectors.toList());
    //    }
    public List<SearchVo> getDatas(SearchForm form) {
        Query query = new Query(Criteria.where(ConfigConstant.ID).in(form.getIdList()));
        return mongoTemplate.find(query, SearchVo.class, form.getBizName());
    }

    public SearchVo getData(SearchForm form) {
        Query query = new Query(Criteria.where(ConfigConstant.ID).is(form.getIdList().get(0)));
        return mongoTemplate.findOne(query, SearchVo.class, form.getBizName());
    }

    /**
     * 保存数据
     *
     * @param mongoDtoList
     */
    //    @Async("taskExecutor")
    public List<String> saveData(List<MongoDto> mongoDtoList, String bizName) {
        /*BulkOperations bulkOperations = mongoTemplate.bulkOps(BulkOperations.BulkMode.UNORDERED, MongoDto.class, bizName);
        bulkOperations.insert(mongoDtoList).execute();*/
        return mongoTemplate.insert(mongoDtoList, bizName).stream().map(mongoDto -> {
            return mongoDto.getId();
        }).collect(Collectors.toList());
    }

    public String saveData(MongoDto mongoDto, String bizName) {
        return mongoTemplate.insert(mongoDto, bizName).getId();
    }

}
