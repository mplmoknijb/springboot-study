package cn.leon.core;

import java.util.List;
import java.util.Map;

import com.sixi.micro.common.dto.PageData;
import cn.leon.domain.entity.MongoDto;
import cn.leon.domain.form.IndexInfoForm;
import cn.leon.domain.form.SearchDetailForm;
import cn.leon.domain.form.SearchForm;
import cn.leon.domain.vo.CountResultVo;

/**
 * @author mujian
 * @Desc
 * @date 2019/7/4 17:14
 */
public interface DataReadOps {

    PageData<Map<String, Object>> getData(SearchForm form);

    MongoDto getDetailById(SearchDetailForm form);

    List<MongoDto> getDetails(SearchDetailForm form);

    String searchDocumentId(SearchForm form);

    List<String> checkDateWithData(IndexInfoForm form);

    CountResultVo countIndexByCodition(SearchForm searchForm);
}
