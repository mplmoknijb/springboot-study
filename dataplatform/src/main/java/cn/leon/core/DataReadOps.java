package cn.leon.core;

import java.util.List;
import java.util.Map;

import com.sixi.micro.common.dto.PageData;
import com.sixi.search.coreservice.domain.entity.MongoDto;
import com.sixi.search.coreservice.domain.form.IndexInfoForm;
import com.sixi.search.coreservice.domain.form.SearchDetailForm;
import com.sixi.search.coreservice.domain.form.SearchForm;
import com.sixi.search.coreservice.domain.vo.CountResultVo;

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
