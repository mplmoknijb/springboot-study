package cn.leon.harbor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * @ClassName HarborConfig
 * @Description
 * @Author Jevon
 * @Date2019/12/30 9:44
 **/
@Configuration
public class HarborConfig {
    public static final String baseUrl = "http://192.168.192.128";
    @Bean
    public HarborClient initHarbor() throws IOException {
        HarborClient harborClient = new HarborClient(baseUrl,false);
        //2) login
        harborClient.login("admin", "Harbor12345");
        return harborClient;
    }
}
