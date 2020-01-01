package cn.leon.core;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sixi.micro.common.dto.PageData;
import com.sixi.search.coreservice.domain.entity.MongoDto;
import com.sixi.search.coreservice.domain.form.IndexInfoForm;
import com.sixi.search.coreservice.domain.form.SearchDetailForm;
import com.sixi.search.coreservice.domain.form.SearchForm;
import com.sixi.search.coreservice.domain.vo.CountResultVo;
import com.sixi.search.coreservice.kits.Elastickit;
import com.sixi.search.coreservice.kits.MongoKit;
import lombok.extern.slf4j.Slf4j;

/**
 * @author mujian
 * @Desc
 * @date 2019/7/4 17:14
 */
@Service
@Slf4j
public class DataReadOpsImpl implements DataReadOps {

    @Autowired
    private MongoKit mongoKit;
    @Autowired
    private Elastickit elastickit;

    @Override
    public PageData<Map<String, Object>> getData(SearchForm form) {
        return elastickit.searchDocument(form);
    }

    @Override
    public MongoDto getDetailById(SearchDetailForm form) {
        return mongoKit.getData(form);
    }

    @Override
    public List<MongoDto> getDetails(SearchDetailForm form) {
        return mongoKit.getDatas(form);
    }

    @Override
    public String searchDocumentId(SearchForm form) {
        return elastickit.searchDocumentId(form);
    }

    @Override
    public List<String> checkDateWithData(IndexInfoForm form) {
        return elastickit.checkDateWithData(form);
    }

    @Override
    public CountResultVo countIndexByCodition(SearchForm searchForm) {
        return elastickit.countIndexByCodition(searchForm);
    }
}
