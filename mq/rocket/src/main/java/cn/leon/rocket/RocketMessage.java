package cn.leon.rocket;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class RocketMessage {
    final public static String TOPIC = "SELF_";
    private Integer id;
}
