package cn.leon.aop;

import cn.leon.utils.DateUtil;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Aspect//定义一个切面处理类
@Order(10)
public class ServiceHandler {

    private final static Logger log = LoggerFactory.getLogger(ServiceHandler.class);

    /**
     * 切面的同步问题
     */
    ThreadLocal<Long> startDt = new ThreadLocal<>();

    /**
     * 切入点：匹配连接点的断言
     * <p>
     * 匹配任何包下的service包及子包下的任何方法执行(该模式能匹配到任何多级的子包下的方法执行)
     */
    @Pointcut("execution(public * *..service..*(..))")
    public void service() {
    }

    ;

    @Before("service()")
    public void beforeAdvice() {
        log.info("ServiceHandlerAop.beforeAdvice...time:" + DateUtil.now());
    }
}
