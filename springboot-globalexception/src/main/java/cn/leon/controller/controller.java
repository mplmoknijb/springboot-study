package cn.leon.controller;

import cn.leon.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class controller {

    private final static Logger logger = LoggerFactory.getLogger(controller.class);


    @RequestMapping("/")
    public String hello(ModelMap map) {
        logger.info("访问到了 hello ...");
        map.addAttribute("index", "一起尝试 spring boot 全局异常捕获吧...");
        return "index";
    }

    @RequestMapping("/anyone")
    public String anyone(ModelMap map) throws Exception {
        logger.info("访问到了 index ...");
        throw new Exception();
    }

    @RequestMapping("/mine")
    public String mine(ModelMap map) {
        logger.info("访问到了\t\tmine ...");
        Throwable throwable = new Throwable();
        throw new BusinessException("我们访问到了 mine ...", throwable);
    }

    @ResponseBody
    @RequestMapping("/sum")
    public String sum(ModelMap map) throws ArithmeticException {
        int i = 1 / 0;
        return "" + i;
    }
}
