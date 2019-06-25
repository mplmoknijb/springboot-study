package cn.leon.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @author mujian
 * @date 2019-5-7 0007
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "test")
public class MongoDto {
    @Id
    private String id;
    @Field("age")
    private Integer age;
    private String name;
}
