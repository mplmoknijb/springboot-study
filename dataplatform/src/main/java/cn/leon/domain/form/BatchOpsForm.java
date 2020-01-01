package cn.leon.domain.form;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author mujian
 * @Desc
 * @date 2019/7/3 13:00
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BatchOpsForm extends HtableOpsForm{
    private Map<String, Map<String, String>> rowMap;
}
