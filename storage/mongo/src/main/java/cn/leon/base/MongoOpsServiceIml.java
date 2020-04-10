package cn.leon.base;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import cn.leon.domain.StorageForm;
import cn.leon.model.MongoDto;
import cn.leon.base.MongoOpsService;

@Service
public class MongoOpsServiceIml implements MongoOpsService {

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

    @Override
    public void saveTest(StorageForm form) {
        List<MongoDto> mongoDtoList = form.getBaseEntityList().stream().map(baseEntity -> {
            return MongoDto.builder().id(baseEntity.getBizKey())
                           .opsDate(form.getOpsTime())
                           .userId(form.getUserId())
                           .detail(baseEntity.getDetail()).build();
        }).collect(Collectors.toList());
        BulkOperations bulkOperations = mongoTemplate.bulkOps(BulkOperations.BulkMode.UNORDERED, MongoDto.class, form.getBizName());
        bulkOperations.insert(mongoDtoList).execute();
        mongoTemplate.inser
    }
}
