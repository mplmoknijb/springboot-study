package cn.leon.domain.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AggregationForm {
  private String bizName;
  private Map<String, String> aggs;
  private Map<String, Object> exact;
  private Map<String, Object> fuzz;
  private String startDt;
  private String endDt;
}
