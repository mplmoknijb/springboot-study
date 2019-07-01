package cn.leon.service;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.leon.domain.form.HtableOpsForm;
import cn.leon.domain.vo.ResultBean;
import cn.leon.kits.ConcurrentKits;
import cn.leon.util.HBaseClient;
import com.google.common.collect.Lists;
import lombok.SneakyThrows;

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
public class HbaseTemplate implements HbaseService {

    @Autowired
    private HBaseClient hBaseClient;

    @Autowired
    private ConcurrentKits concurrentKits;

    private AtomicInteger atomicInteger = new AtomicInteger(1);

    @Override
    @SneakyThrows(IOException.class)
    public void insertColumn(HtableOpsForm form) {
        // 新建表
        hBaseClient.createTable(form);
        for (String family : form.getColumnFamilies()) {
            // 新增数据
            hBaseClient.insertOrUpdate(form.getTableName(),
                                       form.getRowKey(),
                                       family,
                                       form.getColumns()[0],
                                       form.getValue()
            );
        }
    }

    @Override
    public void insertColumList(HtableOpsForm form) {

    }

    @Override
    @SneakyThrows(IOException.class)
    public void insertRowList(HtableOpsForm form) {
        // 新建表
        hBaseClient.createTable(form);
        ThreadPoolExecutor executor = concurrentKits.getThreadPool();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                List<Put> list = Lists.newArrayList();
                for (int i = 0; i < 1000; i++) {
                    int key = atomicInteger.getAndIncrement();
                    Put put = new Put(Bytes.toBytes(key));
                    put.addColumn(Bytes.toBytes(form.getColumnFamilies()[0]), Bytes.toBytes(form.getColumns()[0]), Bytes.toBytes(i));
                    list.add(put);
                }
                try {
                    hBaseClient.insertBatch(list, form.getTableName());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        for (int k = 0; k < 5000; k++) {
            executor.execute(runnable);
        }
        executor.shutdown();
    }

    @Override
    public String getValue(HtableOpsForm form) {
        return hBaseClient.getValue(form.getTableName(), form.getRowKey(), form.getColumnFamilies()[0], form.getColumns()[0]);
    }

    @Override
    public String getRow(HtableOpsForm form) {
        return hBaseClient.getValue(form.getTableName(), form.getRowKey(), form.getColumnFamilies()[0], form.getColumns()[0]);
    }

    @Override
    @SneakyThrows(IOException.class)
    public List<String> getRowList(HtableOpsForm form) {
        return hBaseClient.scanTable(form.getTableName(), form.getValue());
    }
}
