package cn.leon.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

/**
 * @author mujian
 * @Desc
 * @date 2019/6/4 18:05
 */
@Data
@ConfigurationProperties(prefix = "hbase")
public class HbaseProperties {
    private Map<String, String> config;
}
