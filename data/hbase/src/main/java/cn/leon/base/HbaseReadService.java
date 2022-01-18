package cn.leon.base;

import cn.leon.domain.form.HtableOpsForm;

import java.util.List;

/**
 * @author mujian
 * @Desc
 * @date 2019/6/13 10:00
 */
public interface HbaseReadService {

    String getValue(HtableOpsForm form);

    List<String> getRow(HtableOpsForm form);

    List<String> getRowList(HtableOpsForm form);
}
