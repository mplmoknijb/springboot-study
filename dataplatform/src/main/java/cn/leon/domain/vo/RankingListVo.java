package cn.leon.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author mujian
 * @Desc
 * @date 2019/7/15 8:56
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RankingListVo {
    private RankingItemVo item;
    private Integer pageIndex;
    private Integer selectorIndex;
    private Integer pageNum;
    private Integer noADIndex;
}
