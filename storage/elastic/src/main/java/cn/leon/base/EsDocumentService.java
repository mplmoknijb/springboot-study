package cn.leon.base;

import cn.leon.domain.form.IndexForm;

/**
 * @author mujian
 * @Desc
 * @date 2019/7/3 15:09
 */
public interface EsDocumentService {

    void createDocument(IndexForm form);

    boolean bulkInsertDocument(IndexForm form);

    boolean check(IndexForm form);
}
