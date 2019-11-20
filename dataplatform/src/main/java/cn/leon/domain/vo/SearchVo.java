package cn.leon.domain.vo;

import java.util.Date;

import org.joda.time.DateTime;

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
public class SearchVo {
    private String dateTime;
    private String keywords;
    private Integer standardState;
    private Integer standardScore;
    private Integer type;
    private String storageId;
    private Integer pageNum;
    private Integer pageIndex;
    private Integer num;
}
