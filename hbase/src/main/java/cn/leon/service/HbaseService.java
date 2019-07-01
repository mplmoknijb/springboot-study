package cn.leon.service;

import java.io.IOException;
import java.util.List;

import cn.leon.domain.form.HtableOpsForm;

/**
 * @author mujian
 * @Desc
 * @date 2019/6/13 10:00
 */
public interface HbaseService {
    void insertColumn(HtableOpsForm form) throws IOException;

    void insertColumList(HtableOpsForm form);

    void insertRowList(HtableOpsForm form);

    String getValue(HtableOpsForm form);

    String getRow(HtableOpsForm form);

    List<String> getRowList(HtableOpsForm form);
}
