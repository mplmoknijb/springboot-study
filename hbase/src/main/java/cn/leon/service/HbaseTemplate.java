package cn.leon.service;

import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.leon.domain.form.HtableOpsForm;
import cn.leon.kits.ConcurrentKits;
import cn.leon.util.HBaseClient;

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
public class HbaseTemplate {

    @Autowired
    private HBaseClient hBaseClient;

    @Autowired
    private ConcurrentKits concurrentKits;

    public void insertOne(HtableOpsForm form) throws IOException {
        // 新建表
        hBaseClient.createTable(form);
        // 新增数据
        hBaseClient.insertOrUpdate(form.getTableName(),
                                   UUID.randomUUID().toString().replace("-", ""),
                                   "cf1",
                                   new String[]{String.valueOf(Math.random()), String.valueOf(Math.random())},
                                   new String[]{String.valueOf(Math.random()), String.valueOf(Math.random())});
    }

    public void batchSyncData() throws IOException {
        //        ThreadPoolExecutor poolExecutor = concurrentKits.getThreadPool();
        //        Runnable runnable = new Runnable() {
        //            @Override
        //            public void run() {
        //                // 新增数据
        //                try {
        //                    hBaseClient.insertOrUpdate("test", UUID.randomUUID().toString().replace("-",""), "cf1", new String[]{String.valueOf(Math.random()), String.valueOf(Math.random())}, new String[]{String.valueOf(Math.random()), String.valueOf(Math.random())});
        //                } catch (IOException e) {
        //                    e.printStackTrace();
        //                }
        //            }
        //        };
        //        for (int i = 0; i < 10000; i++) {
        //            poolExecutor.execute(runnable);
        //        }
        hBaseClient.insertOrUpdate("test", "test2", "cf1", String.valueOf(1), String.valueOf(1));
    }

    public String getDataByRowkey() {
        return hBaseClient.getValue("test", "2", "cf1", "2");
    }
}
