package cn.leon.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * @author mujian
 * @Desc
 * @date 2019/8/8 9:39
 */
@Data
@ConfigurationProperties(prefix = "data.platform.elastic")
public class ElasticProperties {
    private String host;
    private Integer maxTotal;
}
