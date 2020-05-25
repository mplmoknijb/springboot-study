package cn.leon.lock.model;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
@Data
@ConfigurationProperties("lock")
public class LockProperties {

    private String type;
    private boolean enabled;
    private String prefix;
}
