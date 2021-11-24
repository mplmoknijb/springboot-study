package cn.leon.webflux.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Builder
@Table("reactor")
public class ReactorEntity {

    @Id
    private String id;
    private String name;
    private String lastName;
}
