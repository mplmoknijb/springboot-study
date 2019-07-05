package cn.leon.core;

import java.util.List;

import cn.leon.domain.form.SearchForm;
import cn.leon.domain.form.StorageForm;
import cn.leon.domain.vo.SearchVo;

/**
 * @author mujian
 * @Desc
 * @date 2019/7/4 17:14
 */
public interface DataOps {

    void saveData(StorageForm form);

    List<SearchVo> getData(SearchForm form);
}
