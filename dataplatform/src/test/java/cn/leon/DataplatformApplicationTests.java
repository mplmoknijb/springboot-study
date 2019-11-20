package cn.leon;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.leon.kits.ConcurrentKit;
import cn.leon.kits.MongoKit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DataplatformApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Autowired
    private DataOps dataOps;
    @Autowired
    private MongoKit mongoKit;
    @Autowired
    private ConcurrentKit concurrentKit;

    @Test
    public void saveTest() {
        //        ThreadPoolExecutor poolExecutor = concurrentKit.getThreadPool();
        //        Runnable runnable = new Runnable() {
        //            @Override
        //            public void run() {
        //                List<BaseEntity> list = Lists.newArrayList();
        //                for (int i = 0; i < 1000; i++) {
        //                    Map<String, String> map = Maps.newHashMap();
        //                    map.put("k", String.valueOf(i));
        //                    list.add(BaseEntity.builder()
        //                                       .bizKey(String.valueOf(i))
        //                                       .condition(map)
        //                                       .detail(UUID.randomUUID().toString()).build());
        //                }
        //                dataOps.saveData(StorageForm.builder()
        //                                            .baseEntityList(list)
        //                                            .bizName("report")
        //                                            .opsTime(new Date())
        //                                            .shard(1)
        //                                            .replicate(1)
        //                                            .userId("10086")
        //                                            .build());
        //            }
        //        };
        //        for (int i = 0; i < 5000; i++) {
        //            poolExecutor.execute(runnable);
        //        }
        //        poolExecutor.shutdown();
    }
}
