package cn.leon.config;

import java.util.Map;

import lombok.Data;

/**
 * @author mujian
 * @Desc
 * @date 2019/6/4 18:05
 */
@Data
//@ConfigurationProperties(prefix = "data.platform.hbase")
public class HbaseProperties {
    private Map<String, String> config;
}
