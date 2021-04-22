package cn.leon.core.model;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("lock.zk")
public class ZkInfoProperties {
    private String address;
    private int retrySleepTimeMs = 3 * 1000;
    private int retryCount = 5;
    private int SessionTimeOutMs = 60 * 1000;
    private int connectionTimeOutMs = 15 * 1000;
    private String nameSpace;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getRetrySleepTimeMs() {
        return retrySleepTimeMs;
    }

    public void setRetrySleepTimeMs(int retrySleepTimeMs) {
        this.retrySleepTimeMs = retrySleepTimeMs;
    }

    public int getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }

    public int getSessionTimeOutMs() {
        return SessionTimeOutMs;
    }

    public void setSessionTimeOutMs(int sessionTimeOutMs) {
        SessionTimeOutMs = sessionTimeOutMs;
    }

    public int getConnectionTimeOutMs() {
        return connectionTimeOutMs;
    }

    public void setConnectionTimeOutMs(int connectionTimeOutMs) {
        this.connectionTimeOutMs = connectionTimeOutMs;
    }

    public String getNameSpace() {
        return nameSpace;
    }

    public void setNameSpace(String nameSpace) {
        this.nameSpace = nameSpace;
    }

    public AuthInfo getAuthInfo() {
        return authInfo;
    }

    public void setAuthInfo(AuthInfo authInfo) {
        this.authInfo = authInfo;
    }

    private AuthInfo authInfo;
    public class AuthInfo {
        public String getScheme() {
            return scheme;
        }

        public void setScheme(String scheme) {
            this.scheme = scheme;
        }

        public String getAuth() {
            return auth;
        }

        public void setAuth(String auth) {
            this.auth = auth;
        }

        private String scheme;
        private String auth;
    }
}
