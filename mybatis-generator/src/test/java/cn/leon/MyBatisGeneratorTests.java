package cn.leon;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import cn.leon.generator.MybatisGenerator;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@PropertySource("classpath:application.yml")
public class MyBatisGeneratorTests extends MybatisGenerator {

    private static final Logger log = LoggerFactory.getLogger(MyBatisGeneratorTests.class);

    @Value("${cn.leon.tables}")
    private String tables;

    @Test
    @Override
    public void generator() {
        buildSingleMapper(tables);
//		buildAllMappers();
    }

    private void buildSingleMapper(String... tables) {
        try {
            generateStubs(tables);
            generateDomainConstants(tables);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void buildAllMappers() {
        try {
            super.buildAllMappers();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
