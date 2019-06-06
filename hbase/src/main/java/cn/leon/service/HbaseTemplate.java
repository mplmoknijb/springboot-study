package cn.leon.service;

import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.leon.util.HBaseClient;

/**
 * @author mujian
 * @Desc    1.创建连接Connection
 *          2.从连接中获取表Table
 *          3.操作表
 *          4.关闭表
 *          5.关闭连接
 *
 * @date 2019/6/4 18:13
 */
@Service
public class HbaseTemplate {

    @Autowired
    private HBaseClient hBaseClient;

    public void importData() throws IOException {
        // 新建表
        String[] strings = new String[]{"cf1","cf2"};
        hBaseClient.createTable("test",strings);
        // 新增数据
        hBaseClient.insertOrUpdate("test", UUID.randomUUID().toString().replace("-",""), "cf1", new String[]{"c1", "c2"}, new String[]{"v1", "v22"});


    }
}
