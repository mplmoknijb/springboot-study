package cn.leon.base;

import java.util.List;

import cn.leon.domain.form.HtableOpsForm;

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
