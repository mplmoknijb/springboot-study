package cn.leon.web;

import cn.leon.conf.DefineConfig;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class Controller {

    @Resource
    private DefineConfig defineConfig;

    /**
     * 读取config
     *
     * @return
     */
    @RequestMapping("/conf")
    public String getConfig() {
        System.out.println(defineConfig.toString());
        return defineConfig.toString();
    }
}
