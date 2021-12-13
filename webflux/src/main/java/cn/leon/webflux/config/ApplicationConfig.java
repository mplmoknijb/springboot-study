//package cn.leon.webflux.config;
//
//import io.r2dbc.spi.ConnectionFactories;
//import io.r2dbc.spi.ConnectionFactory;
//import io.r2dbc.spi.ConnectionFactoryOptions;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
//import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
//
//import java.time.Duration;
//
//@Configuration
//@EnableR2dbcRepositories
//class ApplicationConfig extends AbstractR2dbcConfiguration {
//
//    @Override
//    public ConnectionFactory connectionFactory() {
//        ConnectionFactoryOptions options = ConnectionFactoryOptions.builder()
//                .option(ConnectionFactoryOptions.DRIVER, "mysql")
//                .option(ConnectionFactoryOptions.HOST, "127.0.0.1")
//                .option(ConnectionFactoryOptions.USER, "root")
//                .option(ConnectionFactoryOptions.PORT, 3306)
//                .option(ConnectionFactoryOptions.PASSWORD, "")
//                .option(ConnectionFactoryOptions.DATABASE, "r2dbc")
//                .option(ConnectionFactoryOptions.CONNECT_TIMEOUT, Duration.ofSeconds(3))
//                .build();
//        ConnectionFactory connectionFactory = ConnectionFactories.get(options);
//        return connectionFactory;
//    }
//}