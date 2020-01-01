package cn.leon.core;

import java.util.Map;

import com.sixi.search.coreservice.domain.form.AggregationForm;

public interface AggregationService {
    /**
     * 按条件统计
     * @return
     */
    Map<String, Double> aggregationCountIndexByCondition(AggregationForm aggregationForm);
}
