package cn.leon.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import cn.leon.domain.StorageForm;
import cn.leon.model.MongoDto;
import lombok.extern.slf4j.Slf4j;

/**
 * @author mujian
 * @date 2019-5-7 0007
 **/
@Component
@Slf4j
public class MongoOpsDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 创建对象
     */
    public void saveTest(StorageForm form) {
        BulkOperations bulkOperations = mongoTemplate.bulkOps(BulkOperations.BulkMode.UNORDERED, MongoDto.class);
        bulkOperations.insert(form.getBaseEntityList()).execute();
    }

    /**
     * 根据字段查询
     *
     * @param mongoDto
     * @return
     */
    public MongoDto findOneByField(MongoDto mongoDto) {
        Query query = new Query(Criteria.where("name").is(mongoDto.getId()));
        MongoDto mgt = mongoTemplate.findOne(query, MongoDto.class);
        return mgt;
    }

    /**
     * 更新对象
     */
    public void updateTest(MongoDto test) {
        Query query = new Query(Criteria.where("id").is(test.getId()));
        Update update = new Update().set("age", test.getUserId()).set("name", test.getId());
        //更新查询返回结果集的第一条
        mongoTemplate.updateFirst(query, update, MongoDto.class);
        //更新查询返回结果集的所有
        // mongoTemplate.updateMulti(query,update,TestEntity.class);
    }

    /**
     * 删除对象
     *
     * @param id
     */
    public void deleteTestById(Integer id) {
        Query query = new Query(Criteria.where("id").is(id));
        mongoTemplate.remove(query, MongoDto.class);
    }
}
