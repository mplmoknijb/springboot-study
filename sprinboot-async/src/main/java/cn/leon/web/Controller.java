package cn.leon.web;

import cn.leon.service.ArithmeticService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Future;

@RestController
public class Controller {

    private static final Logger logger = LoggerFactory.getLogger(Controller.class);

    @Autowired
    private ArithmeticService arithmeticService;


    @GetMapping("/")
    public void index() {
        long start = System.currentTimeMillis();


        logger.info("--------------------------------------------\n");
        logger.info("每个任务执行的时间是：" + arithmeticService.DoTime + "（毫秒）");

        try {
            Future<Long> task = arithmeticService.subByAsync();

            arithmeticService.subByVoid();

            long sync = arithmeticService.subBySync();

            while (true) {
                if (task.isDone()) {
                    long async = task.get();

                    logger.info("异步任务执行的时间是：" + async + "（毫秒）");
                    // logger.info("注解任务执行的时间是： -- （毫秒）");
                    logger.info("同步任务执行的时间是：" + sync + "（毫秒）");

                    break;
                }
            }
            logger.info("--------------------------------------------\n");
        } catch (Exception e) {
            e.printStackTrace();
        }

        long end = System.currentTimeMillis();
        logger.info("\t........请求响应时间为：" + (end - start) + "（毫秒）");
    }


    @GetMapping("/mine")
    public void mine() {
        for (int i = 0; i < 10; i++) {
            try {
                arithmeticService.doMineAsync(i);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
