package cn.leon.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author mujian
 * @date 2019-5-7 0007
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MongoDto {
    private Integer id;
    private Integer age;
    private String name;
}
