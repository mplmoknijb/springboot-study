package cn.leon.service.Impl;

import cn.leon.model.MongoDto;
import cn.leon.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class MongoOpsServiceIml implements SearchService {

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 按条件分页查询
     *
     * @return
     */
    @Override
    public List<MongoDto> searchPageHelper(MongoDto mongoDto, Integer page, Integer size) {
        Query query = new Query(Criteria.where("age").gte(104));
        // 排序模板
        Sort sort = new Sort(Sort.Direction.DESC, "age");
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        return mongoTemplate.find(query.with(pageRequest), MongoDto.class);
    }
}
