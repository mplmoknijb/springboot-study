package cn.leon.base;

import cn.leon.domain.form.HtableOpsForm;

/**
 * @author mujian
 * @Desc
 * @date 2019/6/13 10:00
 */
public interface HtableOpsService {

    boolean tableExists(String tableName);

    void createTable(HtableOpsForm form);
}
