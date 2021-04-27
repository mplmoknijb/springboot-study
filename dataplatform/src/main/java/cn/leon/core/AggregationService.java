package cn.leon.core;

import cn.leon.domain.form.AggregationForm;

import java.util.Map;

public interface AggregationService {
    /**
     * 按条件统计
     * @return
     */
    Map<String, Double> aggregationCountIndexByCondition(AggregationForm aggregationForm);
}
