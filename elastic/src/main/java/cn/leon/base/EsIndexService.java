package cn.leon.base;

import cn.leon.domain.form.IndexForm;

/**
 * @author mujian
 * @Desc
 * @date 2019/7/3 15:09
 */
public interface EsIndexService {

    void createIndex(IndexForm form);

    boolean dropIndex(IndexForm form);

    boolean checkIndex(IndexForm form);
}
