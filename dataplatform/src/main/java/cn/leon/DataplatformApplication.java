package cn.leon;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import cn.leon.domain.form.BaseEntity;
import cn.leon.domain.form.StorageListForm;
import cn.leon.kits.ConcurrentKit;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@SpringBootApplication
public class DataplatformApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(DataplatformApplication.class, args);
    }

    @Autowired
    private ConcurrentKit concurrentKit;
    @Autowired
    private DataOps dataOps;

    @Override
    public void run(String... args) {
//                this.test();
    }

    private void test() {
        ThreadPoolExecutor poolExecutor = concurrentKit.getThreadPool(100,200,200);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                List<BaseEntity> list = Lists.newArrayList();
                for (int i = 0; i < 1000; i++) {
                    Map<String, Object> condition = Maps.newHashMap();
                    condition.put("k", UUID.randomUUID().toString().replace("-", ""));
                    Map<String, String> detail = Maps.newHashMap();
                    detail.put("list",
                               "{\"sixiId\":\"1010113071008682\",\"reportData\":{\"storeInfo\":{\"storeName\":\"义乌市富石制帽厂\",\"storeurl\":\"https://fushihats.1688.com\",\"account\":\"义乌市富石\"},\"coreData\":{\"totalsales30\":1322,\"onSale\":2874,\"window\":\"6/33\",\"startDt\":\"2019-05-13\",\"endDt\":\"2019-05-19\",\"type\":\"week\",\"showTime\":{\"count\":2874,\"ringRatio\":\"+11.26%\",\"compareYday\":\"-2.19%\",\"peerAverage\":2700},\"pageViews\":{\"count\":405,\"ringRatio\":\"+11.26%\",\"compareYday\":\"-2.19%\",\"peerAverage\":200},\"visitors\":{\"count\":2874,\"ringRatio\":\"+11.26%\",\"compareYday\":\"-2.19%\",\"peerAverage\":2700},\"paidOrders\":{\"count\":92,\"ringRatio\":\"+11.26%\",\"compareYday\":\"-2.19%\",\"peerAverage\":100},\"amount\":{\"count\":23332200,\"ringRatio\":\"+11.26%\",\"compareYday\":\"-2.19%\",\"peerAverage\":22211},\"paidBuyers\":{\"count\":23,\"ringRatio\":\"+11.26%\",\"compareYday\":\"-2.19%\",\"peerAverage\":50},\"paidNewBuyers\":{\"count\":10,\"ringRatio\":\"+11.26%\",\"compareYday\":\"-2.19%\",\"peerAverage\":12},\"customerPrice\":{\"count\":123,\"ringRatio\":\"+11.26%\",\"compareYday\":\"-2.19%\",\"peerAverage\":130},\"paidItems\":{\"count\":555,\"ringRatio\":\"+11.26%\",\"compareYday\":\"-2.19%\",\"peerAverage\":600},\"wxbtgAmount\":{\"count\":483,\"ringRatio\":\"+11.26%\",\"compareYday\":\"-2.19%\",\"peerAverage\":400},\"refundSuccessful\":{\"count\":200,\"ringRatio\":\"+11.26%\",\"compareYday\":\"-2.19%\",\"peerAverage\":111},\"clickConversionRate\":{\"count\":0.4,\"ringRatio\":\"+11.26%\",\"compareYday\":\"-2.19%\",\"peerAverage\":0.1032}}}}{\"sixiId\":\"1010113071008682\",\"reportData\":{\"storeInfo\":{\"storeName\":\"义乌市富石制帽厂\",\"storeurl\":\"https://fushihats.1688.com\",\"account\":\"义乌市富石\"},\"coreData\":{\"totalsales30\":1322,\"onSale\":2874,\"window\":\"6/33\",\"startDt\":\"2019-05-13\",\"endDt\":\"2019-05-19\",\"type\":\"week\",\"showTime\":{\"count\":2874,\"ringRatio\":\"+11.26%\",\"compareYday\":\"-2.19%\",\"peerAverage\":2700},\"pageViews\":{\"count\":405,\"ringRatio\":\"+11.26%\",\"compareYday\":\"-2.19%\",\"peerAverage\":200},\"visitors\":{\"count\":2874,\"ringRatio\":\"+11.26%\",\"compareYday\":\"-2.19%\",\"peerAverage\":2700},\"paidOrders\":{\"count\":92,\"ringRatio\":\"+11.26%\",\"compareYday\":\"-2.19%\",\"peerAverage\":100},\"amount\":{\"count\":23332200,\"ringRatio\":\"+11.26%\",\"compareYday\":\"-2.19%\",\"peerAverage\":22211},\"paidBuyers\":{\"count\":23,\"ringRatio\":\"+11.26%\",\"compareYday\":\"-2.19%\",\"peerAverage\":50},\"paidNewBuyers\":{\"count\":10,\"ringRatio\":\"+11.26%\",\"compareYday\":\"-2.19%\",\"peerAverage\":12},\"customerPrice\":{\"count\":123,\"ringRatio\":\"+11.26%\",\"compareYday\":\"-2.19%\",\"peerAverage\":130},\"paidItems\":{\"count\":555,\"ringRatio\":\"+11.26%\",\"compareYday\":\"-2.19%\",\"peerAverage\":600},\"wxbtgAmount\":{\"count\":483,\"ringRatio\":\"+11.26%\",\"compareYday\":\"-2.19%\",\"peerAverage\":400},\"refundSuccessful\":{\"count\":200,\"ringRatio\":\"+11.26%\",\"compareYday\":\"-2.19%\",\"peerAverage\":111},\"clickConversionRate\":{\"count\":0.4,\"ringRatio\":\"+11.26%\",\"compareYday\":\"-2.19%\",\"peerAverage\":0.1032}}}}{\"sixiId\":\"1010113071008682\",\"reportData\":{\"storeInfo\":{\"storeName\":\"义乌市富石制帽厂\",\"storeurl\":\"https://fushihats.1688.com\",\"account\":\"义乌市富石\"},\"coreData\":{\"totalsales30\":1322,\"onSale\":2874,\"window\":\"6/33\",\"startDt\":\"2019-05-13\",\"endDt\":\"2019-05-19\",\"type\":\"week\",\"showTime\":{\"count\":2874,\"ringRatio\":\"+11.26%\",\"compareYday\":\"-2.19%\",\"peerAverage\":2700},\"pageViews\":{\"count\":405,\"ringRatio\":\"+11.26%\",\"compareYday\":\"-2.19%\",\"peerAverage\":200},\"visitors\":{\"count\":2874,\"ringRatio\":\"+11.26%\",\"compareYday\":\"-2.19%\",\"peerAverage\":2700},\"paidOrders\":{\"count\":92,\"ringRatio\":\"+11.26%\",\"compareYday\":\"-2.19%\",\"peerAverage\":100},\"amount\":{\"count\":23332200,\"ringRatio\":\"+11.26%\",\"compareYday\":\"-2.19%\",\"peerAverage\":22211},\"paidBuyers\":{\"count\":23,\"ringRatio\":\"+11.26%\",\"compareYday\":\"-2.19%\",\"peerAverage\":50},\"paidNewBuyers\":{\"count\":10,\"ringRatio\":\"+11.26%\",\"compareYday\":\"-2.19%\",\"peerAverage\":12},\"customerPrice\":{\"count\":123,\"ringRatio\":\"+11.26%\",\"compareYday\":\"-2.19%\",\"peerAverage\":130},\"paidItems\":{\"count\":555,\"ringRatio\":\"+11.26%\",\"compareYday\":\"-2.19%\",\"peerAverage\":600},\"wxbtgAmount\":{\"count\":483,\"ringRatio\":\"+11.26%\",\"compareYday\":\"-2.19%\",\"peerAverage\":400},\"refundSuccessful\":{\"count\":200,\"ringRatio\":\"+11.26%\",\"compareYday\":\"-2.19%\",\"peerAverage\":111},\"clickConversionRate\":{\"count\":0.4,\"ringRatio\":\"+11.26%\",\"compareYday\":\"-2.19%\",\"peerAverage\":0.1032}}}}{\"sixiId\":\"1010113071008682\",\"reportData\":{\"storeInfo\":{\"storeName\":\"义乌市富石制帽厂\",\"storeurl\":\"https://fushihats.1688.com\",\"account\":\"义乌市富石\"},\"coreData\":{\"totalsales30\":1322,\"onSale\":2874,\"window\":\"6/33\",\"startDt\":\"2019-05-13\",\"endDt\":\"2019-05-19\",\"type\":\"week\",\"showTime\":{\"count\":2874,\"ringRatio\":\"+11.26%\",\"compareYday\":\"-2.19%\",\"peerAverage\":2700},\"pageViews\":{\"count\":405,\"ringRatio\":\"+11.26%\",\"compareYday\":\"-2.19%\",\"peerAverage\":200},\"visitors\":{\"count\":2874,\"ringRatio\":\"+11.26%\",\"compareYday\":\"-2.19%\",\"peerAverage\":2700},\"paidOrders\":{\"count\":92,\"ringRatio\":\"+11.26%\",\"compareYday\":\"-2.19%\",\"peerAverage\":100},\"amount\":{\"count\":23332200,\"ringRatio\":\"+11.26%\",\"compareYday\":\"-2.19%\",\"peerAverage\":22211},\"paidBuyers\":{\"count\":23,\"ringRatio\":\"+11.26%\",\"compareYday\":\"-2.19%\",\"peerAverage\":50},\"paidNewBuyers\":{\"count\":10,\"ringRatio\":\"+11.26%\",\"compareYday\":\"-2.19%\",\"peerAverage\":12},\"customerPrice\":{\"count\":123,\"ringRatio\":\"+11.26%\",\"compareYday\":\"-2.19%\",\"peerAverage\":130},\"paidItems\":{\"count\":555,\"ringRatio\":\"+11.26%\",\"compareYday\":\"-2.19%\",\"peerAverage\":600},\"wxbtgAmount\":{\"count\":483,\"ringRatio\":\"+11.26%\",\"compareYday\":\"-2.19%\",\"peerAverage\":400},\"refundSuccessful\":{\"count\":200,\"ringRatio\":\"+11.26%\",\"compareYday\":\"-2.19%\",\"peerAverage\":111},\"clickConversionRate\":{\"count\":0.4,\"ringRatio\":\"+11.26%\",\"compareYday\":\"-2.19%\",\"peerAverage\":0.1032}}}}{\"sixiId\":\"1010113071008682\",\"reportData\":{\"storeInfo\":{\"storeName\":\"义乌市富石制帽厂\",\"storeurl\":\"https://fushihats.1688.com\",\"account\":\"义乌市富石\"},\"coreData\":{\"totalsales30\":1322,\"onSale\":2874,\"window\":\"6/33\",\"startDt\":\"2019-05-13\",\"endDt\":\"2019-05-19\",\"type\":\"week\",\"showTime\":{\"count\":2874,\"ringRatio\":\"+11.26%\",\"compareYday\":\"-2.19%\",\"peerAverage\":2700},\"pageViews\":{\"count\":405,\"ringRatio\":\"+11.26%\",\"compareYday\":\"-2.19%\",\"peerAverage\":200},\"visitors\":{\"count\":2874,\"ringRatio\":\"+11.26%\",\"compareYday\":\"-2.19%\",\"peerAverage\":2700},\"paidOrders\":{\"count\":92,\"ringRatio\":\"+11.26%\",\"compareYday\":\"-2.19%\",\"peerAverage\":100},\"amount\":{\"count\":23332200,\"ringRatio\":\"+11.26%\",\"compareYday\":\"-2.19%\",\"peerAverage\":22211},\"paidBuyers\":{\"count\":23,\"ringRatio\":\"+11.26%\",\"compareYday\":\"-2.19%\",\"peerAverage\":50},\"paidNewBuyers\":{\"count\":10,\"ringRatio\":\"+11.26%\",\"compareYday\":\"-2.19%\",\"peerAverage\":12},\"customerPrice\":{\"count\":123,\"ringRatio\":\"+11.26%\",\"compareYday\":\"-2.19%\",\"peerAverage\":130},\"paidItems\":{\"count\":555,\"ringRatio\":\"+11.26%\",\"compareYday\":\"-2.19%\",\"peerAverage\":600},\"wxbtgAmount\":{\"count\":483,\"ringRatio\":\"+11.26%\",\"compareYday\":\"-2.19%\",\"peerAverage\":400},\"refundSuccessful\":{\"count\":200,\"ringRatio\":\"+11.26%\",\"compareYday\":\"-2.19%\",\"peerAverage\":111},\"clickConversionRate\":{\"count\":0.4,\"ringRatio\":\"+11.26%\",\"compareYday\":\"-2.19%\",\"peerAverage\":0.1032}}}}");
                    list.add(BaseEntity.builder()
                                       .bizKey(String.valueOf(i))
                                       .condition(condition)
                                       .detail(detail)
                                       .build());
                }
                dataOps.saveData(StorageListForm.builder()
                                                .baseEntityList(list)
                                                .bizName("report")
                                                .opsTime(new Date())
                                                .shard(3)
                                                .replicate(1)
                                                .userId("10086")
                                                .build());
            }
        };
        for (int i = 0; i < 10000; i++) {
            poolExecutor.execute(runnable);
        }
        poolExecutor.shutdown();
    }
}
