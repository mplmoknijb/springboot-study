package cn.leon.base;

import cn.leon.domain.form.BatchOpsForm;
import cn.leon.domain.form.HtableOpsForm;
import cn.leon.util.HBaseClient;
import com.google.common.collect.Lists;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author mujian
 * @Desc 1.创建连接Connection
 * 2.从连接中获取表Table
 * 3.操作表
 * 4.关闭表
 * 5.关闭连接
 * @date 2019/6/4 18:13
 */
@Service
public class HbaseWriteServiceimpl implements HbaseWriteService {

    @Autowired
    private HBaseClient hBaseClient;

    @Override
    public void insertColums(HtableOpsForm form) {
        hBaseClient.insertColumn(form.getTableName(), form.getRowKey(), form.getColumnFamilies(), form.getCloumnValue());
    }

    @Override
    public void insertRowList(BatchOpsForm form) {
        // 新建表
        hBaseClient.createTable(form);
        List<Put> list = Lists.newArrayList();
        for (Map.Entry<String, Map<String, String>> entry : form.getRowMap().entrySet()) {
            Put put = new Put(Bytes.toBytes(entry.getKey()));
            for (Map.Entry<String, String> column : entry.getValue().entrySet()) {
                put.addColumn(Bytes.toBytes(form.getColumnFamilies()), Bytes.toBytes(column.getKey()), Bytes.toBytes(column.getValue()));
                list.add(put);
            }
        }
        hBaseClient.insertBatch(list, form.getTableName());
    }
}
