package cn.leon.domain.form;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author mujian
 * @Desc
 * @date 2019/7/5 10:38
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EsStorageForm {
    private Map<String, ? extends Object> condition;
    private String bizKey;
    private String bizName;
}
