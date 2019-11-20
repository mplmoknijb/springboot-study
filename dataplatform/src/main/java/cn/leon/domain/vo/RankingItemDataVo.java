package cn.leon.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author mujian
 * @Desc
 * @date 2019/7/15 13:42
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RankingItemDataVo {
    private String img;
    private String loginId;
    private String price;
    private String shopName;
    private String title;
    private String totalSales30;
    private String shopUrl;
    private boolean isAD;
    private String url;
    private String memberId;
}
