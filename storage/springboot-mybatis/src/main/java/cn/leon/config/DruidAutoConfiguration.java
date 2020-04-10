package cn.leon.config;


import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
@EnableConfigurationProperties(DruidProperties.class)
@ConditionalOnClass(DruidDataSource.class)
@ConditionalOnProperty(prefix = "druid", name = "url")
@AutoConfigureBefore(DataSourceAutoConfiguration.class)
public class DruidAutoConfiguration{

    @Autowired
    private DruidProperties druidProperties;

    @Bean
    public DataSource dataSource() {
        DruidDataSource duridDataSource = new DruidDataSource();
        duridDataSource.setUrl(druidProperties.getUrl());
        duridDataSource.setUsername(druidProperties.getUsername());
        duridDataSource.setPassword(druidProperties.getPassword());
        if (druidProperties.getInitialSize() > 0) {
            duridDataSource.setInitialSize(druidProperties.getInitialSize());
        }
        if (druidProperties.getMinIdle() > 0) {
            duridDataSource.setMinIdle(druidProperties.getMinIdle());
        }
        if (druidProperties.getMaxActive() > 0) {
            duridDataSource.setMaxActive(druidProperties.getMaxActive());
        }
        duridDataSource.setTestOnBorrow(druidProperties.isTestOnBorrow());

        try {
            duridDataSource.init();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return duridDataSource;

    }
}
