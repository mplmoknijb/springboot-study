package cn.leon.core;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.leon.domain.form.AggregationForm;
import cn.leon.kits.Elastickit;

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
