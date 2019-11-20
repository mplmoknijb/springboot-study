package cn.leon.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author mujian
 * @Desc
 * @date 2019/7/15 8:54
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RankingItemVo {
    private String exception;

    private RankingItemDataVo data;
}
