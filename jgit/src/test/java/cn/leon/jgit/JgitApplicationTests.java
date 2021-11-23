package cn.leon.jgit;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;

@SpringBootTest
class JgitApplicationTests {

    @Test
    void contextLoads() throws FileNotFoundException {
        File file = ResourceUtils.getFile("classpath:application.yml");
        System.out.println(file.length());

    }

}
