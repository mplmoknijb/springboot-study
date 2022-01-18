package cn.leon.base;

import cn.leon.domain.form.BatchOpsForm;
import cn.leon.domain.form.HtableOpsForm;

/**
 * @author mujian
 * @Desc
 * @date 2019/6/13 10:00
 */
public interface HbaseWriteService {

    void insertColums(HtableOpsForm form);

    void insertRowList(BatchOpsForm form);
}
