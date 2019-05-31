package cn.leon.dao;

import cn.leon.model.MongoDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author mujian
 * @date 2019-5-7 0007
 **/
@Component
@Slf4j
public class MongoTestDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 创建对象
     */
    public void saveTest() {
        long start = System.currentTimeMillis();
        Runnable runnable = new Runnable() {
            String tName = Thread.currentThread().getName();

            @Override
            public void run() {
                log.info("current=========={}-{}===============", tName, Thread.currentThread().getId());
                List<MongoDto> list = Collections.synchronizedList(new ArrayList<>());
                for (int i = 0; i < 2000; i++) {
                    int n = (int) (Math.random() * 100000);
                    MongoDto mongoDto = MongoDto.builder()
                            .age(n)
                            .name(Thread.currentThread().getName())
                            .id(UUID.randomUUID().toString().replace("-", ""))
                            .build();
                    list.add(mongoDto);
                }
                BulkOperations bulkOperations = mongoTemplate.bulkOps(BulkOperations.BulkMode.UNORDERED, MongoDto.class);

                bulkOperations.insert(list).execute();
//                mongoTemplate.insert(mongoDto, UUID.randomUUID().toString());
            }
        };
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                500,
                700,
                200,
                TimeUnit.MICROSECONDS,
                new LinkedBlockingDeque<Runnable>());
        for (int i = 0; i < 1000000; i++) {
            executor.execute(runnable);
        }
        executor.shutdown();
        long end = System.currentTimeMillis();
        log.info("Total Time is ======================={}===================", end - start);
    }

    /**
     * 根据字段查询
     *
     * @param mongoDto
     * @return
     */
    public MongoDto findOneByField(MongoDto mongoDto) {
        Query query = new Query(Criteria.where("name").is(mongoDto.getName()));
        MongoDto mgt = mongoTemplate.findOne(query, MongoDto.class);
        return mgt;
    }

    /**
     * 更新对象
     */
    public void updateTest(MongoDto test) {
        Query query = new Query(Criteria.where("id").is(test.getId()));
        Update update = new Update().set("age", test.getAge()).set("name", test.getName());
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
