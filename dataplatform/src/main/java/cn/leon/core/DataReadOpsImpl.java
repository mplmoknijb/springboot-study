package cn.leon.core;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sixi.micro.common.dto.PageData;
import cn.leon.domain.entity.MongoDto;
import cn.leon.domain.form.IndexInfoForm;
import cn.leon.domain.form.SearchDetailForm;
import cn.leon.domain.form.SearchForm;
import cn.leon.domain.vo.CountResultVo;
import cn.leon.kits.Elastickit;
import cn.leon.kits.MongoKit;
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
