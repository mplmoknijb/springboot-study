package cn.leon.domain.form;

import java.util.Date;
import java.util.List;
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

    private List<String> idList;
    /*
    业务主键
     */
    private String bizKey;
    /*
    条件
     */
    private Map<String, Object> condition;
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
    private Date startDt;
    private Date endDt;
    @Builder.Default
    private Integer page = 0;
    @Builder.Default
    private Integer num = 100;
}
