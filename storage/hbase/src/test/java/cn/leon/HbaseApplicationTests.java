package cn.leon;

import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.leon.domain.form.BatchOpsForm;
import cn.leon.base.HbaseWriteService;
import com.google.common.collect.Maps;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HbaseApplicationTests {

    @Autowired
    private HbaseWriteService hbaseWriteService;

    @Test
    public void contextLoads() {
    }

    @Test
    public void insertTest() {
        Map<String, String> map = Maps.newHashMap();
        map.put("column",
                "{\"sixiId\":\"1010113071008682\",\"reportData\":{\"storeInfo\":{\"storeName\":\"义乌市富石制帽厂\",\"storeurl\":\"https://fushihats.1688.com\",\"account\":\"义乌市富石\"},\"coreData\":{\"totalsales30\":1322,\"onSale\":2874,\"window\":\"6/33\",\"startDt\":\"2019-05-13\",\"endDt\":\"2019-05-19\",\"type\":\"week\",\"showTime\":{\"count\":2874,\"ringRatio\":\"+11.26%\",\"compareYday\":\"-2.19%\",\"peerAverage\":2700},\"pageViews\":{\"count\":405,\"ringRatio\":\"+11.26%\",\"compareYday\":\"-2.19%\",\"peerAverage\":200},\"visitors\":{\"count\":2874,\"ringRatio\":\"+11.26%\",\"compareYday\":\"-2.19%\",\"peerAverage\":2700},\"paidOrders\":{\"count\":92,\"ringRatio\":\"+11.26%\",\"compareYday\":\"-2.19%\",\"peerAverage\":100},\"amount\":{\"count\":23332200,\"ringRatio\":\"+11.26%\",\"compareYday\":\"-2.19%\",\"peerAverage\":22211},\"paidBuyers\":{\"count\":23,\"ringRatio\":\"+11.26%\",\"compareYday\":\"-2.19%\",\"peerAverage\":50},\"paidNewBuyers\":{\"count\":10,\"ringRatio\":\"+11.26%\",\"compareYday\":\"-2.19%\",\"peerAverage\":12},\"customerPrice\":{\"count\":123,\"ringRatio\":\"+11.26%\",\"compareYday\":\"-2.19%\",\"peerAverage\":130},\"paidItems\":{\"count\":555,\"ringRatio\":\"+11.26%\",\"compareYday\":\"-2.19%\",\"peerAverage\":600},\"wxbtgAmount\":{\"count\":483,\"ringRatio\":\"+11.26%\",\"compareYday\":\"-2.19%\",\"peerAverage\":400},\"refundSuccessful\":{\"count\":200,\"ringRatio\":\"+11.26%\",\"compareYday\":\"-2.19%\",\"peerAverage\":111},\"clickConversionRate\":{\"count\":0.4,\"ringRatio\":\"+11.26%\",\"compareYday\":\"-2.19%\",\"peerAverage\":0.1032}}}}");
        BatchOpsForm form = new BatchOpsForm();
        Map<String, Map<String, String>> putMap = Maps.newHashMap();
        for (int i = 0; i < 5000000; i++) {
            putMap.put(String.valueOf(i), map);
        }
        form.setRowMap(putMap);
        form.setColumnFamilies("cf");
        form.setCloumnValue(map);
        hbaseWriteService.insertRowList(form);

    }
}
