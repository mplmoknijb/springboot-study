package cn.leon.core;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sixi.search.coreservice.domain.form.AggregationForm;
import com.sixi.search.coreservice.kits.Elastickit;

@Service
public class AggregationServiceImpl implements AggregationService {

    @Autowired
    private Elastickit elastickit;

    /**
     * 按条件统计
     *
     * @return
     */
    public Map<String, Double> aggregationCountIndexByCondition(AggregationForm aggregationForm) {
        return elastickit.aggregationCountIndexByCondition(aggregationForm);
    }
}
