package cn.leon.domain.form;


import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author mujian
 * @Desc
 * @date 2019/7/3 17:57
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StorageListForm {
    private List<BaseEntity> baseEntityList;
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
    private Integer shard;

    private Integer replicate;

    private String expireTime;
}
