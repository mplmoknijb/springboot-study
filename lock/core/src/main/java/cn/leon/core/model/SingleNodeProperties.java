package cn.leon.core.model;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
@Data
@ConfigurationProperties("lock.redlock.single")
public class SingleNodeProperties {
//        private List<SingleNode> singleNodeList;
//        private int timeout;
//
//        @Data
//        public class SingleNode {
            private String address;
            private String password;
            private int database;
            private int ssl;
//        }

}
