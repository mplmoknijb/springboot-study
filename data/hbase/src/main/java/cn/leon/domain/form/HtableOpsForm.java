package cn.leon.domain.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @author mujian
 * @Desc
 * @date 2019/6/13 9:56
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HtableOpsForm {
    private String tableName;
    private String columnFamilies;
    private Map<String, String> cloumnValue;
    private String rowKey;
}
