package cn.leon.domain.form;

import java.util.Map;

import com.sun.tools.javac.code.Type;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author mujian
 * @Desc
 * @date 2019/7/3 16:01
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IndexForm {
    private String indices;
    private Integer shard;
    private Integer replicas;
    private Map<String, Object> mapping;
}
