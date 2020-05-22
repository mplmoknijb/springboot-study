package cn.leon.lock.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("lock.zk")
public class ZkInfoProperties {
    private String address;
    @Builder.Default
    private int retrySleepTimeMs = 3 * 1000;
    @Builder.Default
    private int retryCount = 5;
    @Builder.Default
    private int SessionTimeOutMs = 60 * 1000;
    @Builder.Default
    private int connectionTimeOutMs = 15 * 1000;
    private String nameSpace;
    private AuthInfo authInfo;
    @Data
    public class AuthInfo {
        private String scheme;
        private String auth;
    }
}
