package cn.leon.base;

import cn.leon.domain.form.HtableOpsForm;
import cn.leon.util.HBaseClient;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @author mujian
 * @Desc
 * @date 2019/6/13 10:00
 */
@Service
public class HtableOpsServiceImpl implements HtableOpsService {

    @Autowired
    private HBaseClient hBaseClient;

    @Override
    @SneakyThrows(IOException.class)
    public boolean tableExists(String tableName) {
        return hBaseClient.tableExists(tableName);
    }

    @Override
    public void createTable(HtableOpsForm form) {
        hBaseClient.createTable(form);
    }
}
