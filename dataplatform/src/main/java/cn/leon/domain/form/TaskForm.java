package cn.leon.domain.form;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author mujian
 * @Desc
 * @date 2019/7/9 15:17
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskForm {
    /*
    业务主键
     */
    private String bizKey;
    /*
    附属信息k,v
     */
    private Map<String, String> contidion;
    /*
    查询频率
    日 - 0
    月 - 1
    周 - 2
     */
    private Integer type;
    /*
    指令
     */
    private String cmd;
    /*
    平台类型
     */
    private String platform;
    /*
    业务名称  例: "keyword", "storeReport"
     */
    private String bizName;
}
