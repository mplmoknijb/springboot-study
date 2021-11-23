package cn.leon.jgit;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JgitApplication implements CommandLineRunner {
//    @Autowired
//    private GitOperation gitOperation;
    public static void main(String[] args) {
        SpringApplication.run(JgitApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
//        gitOperation.pull();
    }
}
