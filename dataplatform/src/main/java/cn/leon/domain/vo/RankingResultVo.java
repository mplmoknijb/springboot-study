package cn.leon.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author mujian
 * @Desc
 * @date 2019/7/15 13:53
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RankingResultVo {
    private Integer targetPagenum;
    private Integer targetPageindex;
}
