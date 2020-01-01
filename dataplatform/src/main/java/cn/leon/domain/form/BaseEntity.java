package cn.leon.domain.form;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author mujian
 * @Desc
 * @date 2019/7/4 10:16
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BaseEntity {

    private String bizKey;

    private Map<String, Object> condition;

    private Map<String, String> detail;
}
