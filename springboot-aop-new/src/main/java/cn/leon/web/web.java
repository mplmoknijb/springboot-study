package cn.leon.web;

import cn.leon.service.CommonService;
import cn.leon.service.order.OrderInfoService;
import cn.leon.service.user.UserInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class web {

    private final static Logger logger = LoggerFactory.getLogger(web.class);
    @Resource
    private CommonService commonService;

    @Resource
    private OrderInfoService orderInfoService;

    @Resource
    private UserInfoService userInfoService;

    @GetMapping("/*")
    public Long get() {
        logger.info("==================== Aop ==========================");
        int i = 1;
        i = commonService.add(i);
        logger.info("\n====================处理结果为:" + i + "====================\n");
        String name = "#####";
        name = orderInfoService.addUserInfo(name);
        name = userInfoService.addUserInfo(name);
        logger.info("\n====================名称为\t" + name + "====================\n");
        return System.currentTimeMillis();
    }
}
