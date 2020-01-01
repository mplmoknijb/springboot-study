package cn.leon.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author mujian
 * @Desc
 * @date 2019/7/15 13:49
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReqDataVo {
    private String shopName;
    private String memberId;
    private RankingResultVo target1;
    private RankingResultVo target2;
}
