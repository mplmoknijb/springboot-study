package cn.leon.testlog4j;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Testlog4jApplication {

    public static void main(String[] args) {
//        System.setProperty("com.sun.jndi.rmi.object.trustURLCodebase", "true");
//        System.setProperty("com.sun.jndi.ldap.object.trustURLCodebase", "true");
        SpringApplication.run(Testlog4jApplication.class, args);
    }
}
