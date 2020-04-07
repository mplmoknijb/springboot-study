package cn.leon.rocket;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RocketMessage {
    final public static String TOPIC = "SELF_";
    private Integer id;
}
