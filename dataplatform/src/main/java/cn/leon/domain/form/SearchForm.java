package cn.leon.domain.form;

import java.util.Date;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author mujian
 * @Desc
 * @date 2019/7/3 18:00
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchForm {

    private String bizKey;

    private Map<String, String> condition;
    /*
        业务名称
     */
    private String bizName;
    /*
    操作人Id
     */
    private String userId;
    /*
    操作时间
     */
    private Date opsTime;
}
