package cn.leon.base;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.leon.domain.form.HtableOpsForm;
import cn.leon.base.HbaseReadService;
import cn.leon.util.HBaseClient;
import lombok.SneakyThrows;

/**
 * @author mujian
 * @date 2019/6/4 18:13
 */
@Service
public class HbaseReadServiceimpl implements HbaseReadService {

    @Autowired
    private HBaseClient hBaseClient;


    @Override
    public String getValue(HtableOpsForm form) {
        Map.Entry<String, String> entry = form.getCloumnValue().entrySet().iterator().next();
        return hBaseClient.getValue(form.getTableName(), form.getRowKey(), form.getColumnFamilies(), entry.getKey());
    }

    @Override
    @SneakyThrows(IOException.class)
    public List<String> getRow(HtableOpsForm form) {
        return hBaseClient.selectOneRow(form.getTableName(), form.getRowKey());
    }

    @Override
    @SneakyThrows(IOException.class)
    public List<String> getRowList(HtableOpsForm form) {
        Map.Entry<String, String> entry = form.getCloumnValue().entrySet().iterator().next();
        return hBaseClient.scanTable(form.getTableName(), entry.getValue());
    }
}
