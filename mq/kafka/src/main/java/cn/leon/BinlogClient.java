package cn.leon;

import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author mujian
 * @date 2019-5-9 0009
 **/
//@Component
public class BinlogClient implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        // 获取监听数据表数组
        List<String> databaseList = Arrays.asList("test.collecting_store_keywords".split(","));
        HashMap<Long, String> tableMap = new HashMap<Long, String>();
        // 创建binlog监听客户端
        BinaryLogClient client = new BinaryLogClient("localhost", 3306, "root", "root");
        client.setServerId(9099);
        client.registerEventListener((event -> {
            // binlog事件
            EventData data = event.getData();
            if (data != null) {
                if (data instanceof TableMapEventData) {
                    TableMapEventData tableMapEventData = (TableMapEventData) data;
                    tableMap.put(tableMapEventData.getTableId(), tableMapEventData.getDatabase() + "." + tableMapEventData.getTable());
                }
                // update数据
                if (data instanceof UpdateRowsEventData) {
                    UpdateRowsEventData updateRowsEventData = (UpdateRowsEventData) data;
                    String tableName = tableMap.get(updateRowsEventData.getTableId());
                    if (tableName != null && databaseList.contains(tableName)) {
                        String eventKey = tableName + ".update";
                        for (Map.Entry<Serializable[], Serializable[]> row : updateRowsEventData.getRows()) {
//                            String msg = JSON.toJSONString(new BinlogDto(eventKey, row.getValue()));
//                            binlogSend.send(MessageBo.builder().content(msg).build());
                        }
                    }
                }
                // insert数据
                else if (data instanceof WriteRowsEventData) {
                    WriteRowsEventData writeRowsEventData = (WriteRowsEventData) data;
                    String tableName = tableMap.get(writeRowsEventData.getTableId());
                    if (tableName != null && databaseList.contains(tableName)) {
                        String eventKey = tableName + ".insert";
                        for (Serializable[] row : writeRowsEventData.getRows()) {
//                            String msg = JSON.toJSONString(new BinlogDto(eventKey, row));
//                            binlogSend.send(MessageBo.builder().content(msg).build());
                        }
                    }
                }
                // delete数据
                else if (data instanceof DeleteRowsEventData) {
                    DeleteRowsEventData deleteRowsEventData = (DeleteRowsEventData) data;
                    String tableName = tableMap.get(deleteRowsEventData.getTableId());
                    if (tableName != null && databaseList.contains(tableName)) {
                        String eventKey = tableName + ".delete";
                        for (Serializable[] row : deleteRowsEventData.getRows()) {
//                            String msg = JSON.toJSONString(new BinlogDto(eventKey, row));
//                            binlogSend.send(MessageBo.builder().content(msg).build());
                        }
                    }
                }
            }
        }));
        client.connect();
    }
}
