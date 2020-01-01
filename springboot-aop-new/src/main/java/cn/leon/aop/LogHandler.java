package cn.leon.aop;

import cn.leon.utils.DateUtil;
import com.alibaba.fastjson.JSON;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @Order(1) 越小，越优先执行，排在最外层
 * <p>
 * 尝试自定义注解封装(@Component/@Aspect)运行报错
 */
@Component
@Aspect
@Order(1)
public class LogHandler {

    private final static Logger log = LoggerFactory.getLogger(LogHandler.class);

    /**
     * AOP切面中的同步问题：用于监控业务处理性能
     */
    ThreadLocal<Long> startTime = new ThreadLocal<>();

    /**
     * 一、execution(方法表达式)
     * 1、匹配方法时，只能匹配到实现类，匹配到接口类不能成功
     * 2、匹配方法执行
     */

    // 匹配cn.timebusker.service包及子包下的任何方法执行
    @Pointcut("execution(* cn.leon.base.*.*(..))")
    public void log1() {

    }

    // 匹配任何包下的service包及子包下的任何方法执行(该模式只能匹配到一级的子包，多级子包不适用)
    @Pointcut("execution(* *..service..*(..))")
    public void log2() {

    }

    // 匹配任何包下的service包及子包下的任何方法执行(该模式能匹配到任何多级的子包下的方法执行)
    @Pointcut(value = "execution(* *..service..*(..))")
    public void log3() {
    }

    // 匹配返回值类型为java.lang.String的任何包下的service包及子包下的方法执行
    @Pointcut("execution(java.lang.String *..service..*(..))")
    public void log4() {

    }

    // 匹配返回值类型为int的任何包下的service包及子包下的方法执行
    @Pointcut("execution(int *..service..*(..))")
    public void log5() {

    }

    // 匹配任何返回值类型的cn.timebusker包及任何子包下的以add开头的参数为Strign类型的方法执行
    @Pointcut("execution(* cn.leon.base..add*(String))")
    public void log6() {

    }

    // 匹配 OR、AND
    @Pointcut("execution(* cn.leon.base.*.add*(int))")
    public void log7() {

    }

    // 匹配 OR、AND、
    @Pointcut(value = "execution(* cn.leon.base.*.add*(int)) OR execution(* cn.leon..add*(String))")
    public void log8() {
    }


    /**
     * 二、within(类型表达式)
     * 1、匹配类型时，只能匹配到实现类，匹配到接口类不能成功
     * 2、匹配指定类型内的方法执行；
     */
    // 匹配指定类型内的方法执行--只能匹配类型
    @Pointcut("within(cn.leon.base.order.Impl.OrderInfoServiceImpl))")
    public void logw1() {
    }

    @Pointcut("within(cn.leon.base.order.Impl.*))")
    public void logw2() {
    }

    /**
     * 三、this(类型全限定名)
     * 1、可以直接匹配接口类型完成  类型全名限定匹配
     * 2、注意是AOP代理对象的类型匹配，这样就可能包括引入接口方法也可以匹配；注意this中使用的表达式必须是类型全限定名，不支持通配符
     */
    // 匹配指定类型内的方法执行(包下所有的类)
    @Pointcut(value = "this(cn.leon.base.order.OrderInfoService)")
    public void logt1() {
    }

    /**
     * 四、target(类型全限定名)--匹配当前目标对象类型的执行方法
     * 1、可以直接匹配接口类型完成  类型全名限定匹配
     * 2、注意是目标对象的类型匹配，这样就不包括引入接口也类型匹配；注意target中使用的表达式必须是类型全限定名，不支持通配符
     */
    // 匹配指定类型内的方法执行(包下所有的类)
    @Pointcut("target(cn.leon.base.order.OrderInfoService)")
    public void logt2() {

    }

    /**
     * 六、@within(注解类型)--匹配所以持有指定注解类型内的方法；注解类型也必须是全限定类型名；
     * 1、注解类型也必须是全限定类型名;
     */
    // 匹配被org.springframework.stereotype.Service这个注解标注的类----注解标注在接口上不起作用
    @Pointcut(value = "@within(org.springframework.stereotype.Service)")
    public void logaw1() {
    }

    // 匹配 自定义注解标注的类----注解标注在接口上不起作用
    @Pointcut(value = "@within(cn.leon.annotation.leonBean)")
    public void logaw2() {
    }

    /**
     * 八、@annotation(注解类型)--匹配当前执行方法持有指定注解的方法
     * 1、注解类型也必须是全限定类型名；
     */
    @Pointcut("@annotation(cn.leon.annotation.leonMethod)")
    public void logMj() {

    }

    // 引用多个切入点
//	@Before("log6() OR log7()")
// 引用单个切入点
    @Before("logMj()")
    public void beforeAdvice(JoinPoint joinPoint) {
        log.info("====================================================>");
        log.info("LoggerHandlerAop.beforeAdvice...time:" + DateUtil.now());
        Signature signature = joinPoint.getSignature();
        log.info("所属类名称：" + signature.getDeclaringTypeName() + "\n代理类：" + signature.getClass() + "\n方法名称：" + signature.getName() + "\n所属类：" + signature.getDeclaringType());
        Object[] args = joinPoint.getArgs();
        log.info("参数是：" + JSON.toJSONString(args));
        log.info("被织入的对象是：" + joinPoint.getTarget());
    }

    /**
     * 返回后通知：在连接点正常执行完后执行的通知
     */
    @AfterReturning("logMj()")
    public void afterReturning() {
        log.info("LoggerHandlerAop.afterReturningAdvice...time:" + DateUtil.now());
    }

    /**
     * 抛出异常后通知：在连接节点抛出异常退出时执行的通知
     */
    @AfterThrowing("logMj()")
    public void afterThrowingAdvice() {
        log.info("LoggerHandlerAop.afterThrowingAdvice...time:" + DateUtil.now());
    }

    @After("logMj()")
    public void after() {
        log.info("LoggerHandlerAop.after...time:" + DateUtil.now());
    }

    @Around("logMj()")
    public Object around(ProceedingJoinPoint point) {
        log.info("LoggerHandlerAop.aroundAdvice...time:" + DateUtil.now());
        Object p = null;
        try {
            log.info("Around Start==============================>");
            p = point.proceed();
            log.info("Around End==============================>");
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return p;
    }
}
