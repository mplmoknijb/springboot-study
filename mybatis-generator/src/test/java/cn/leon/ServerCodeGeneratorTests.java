package cn.leon;

import org.junit.Test;
import org.junit.runner.RunWith;
import cn.leon.generator.ServerCodeGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@PropertySource("classpath:application.yml")
public class ServerCodeGeneratorTests extends ServerCodeGenerator {

    private static final Logger log = LoggerFactory.getLogger(ServerCodeGeneratorTests.class);

    @Value("${cn.leon.tables}")
    private List<String> tables;

    @Test
    @Override
    public void generator() {
		buildSingleRepository("art_link");
    }

    private void buildSingleRepository(String... tables) {
        try {
            generateStubs(tables);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
