package cn.leon.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.context.annotation.Bean;

public class MyBatisConfig {
    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactoryBean() {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        //  bean.setDataSource(dataSource());
        bean.setTypeAliasesPackage("com.lkt.Professional.entity");
        try {
            //基于注解扫描Mapper，不需配置xml路径
//            bean.setMapperLocations(resolver.getResources("classpath:mappers/*.xml"));
            return bean.getObject();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }
}
