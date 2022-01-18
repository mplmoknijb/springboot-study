package cn.leon.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

/**
 * @author mujian
 * @date 2019-5-7 0007
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MongoDto {
    @Id
    private String id;
    @Field("userId")
    private String userId;
    @Field("opsDate")
    private Date opsDate;
    @Field("detail")
    private String detail;
}
