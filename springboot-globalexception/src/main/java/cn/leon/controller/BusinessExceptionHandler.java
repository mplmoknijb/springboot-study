package cn.leon.controller;

import cn.leon.exception.BusinessException;
import cn.leon.exception.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * 创建全局异常处理类：通过使用@ControllerAdvice定义统一的异常处理类，而不是在每个Controller中逐个定义。
 *
 * @ControllerAdvice统一定义不同Exception映射到不同错误处理页面
 */
@RestControllerAdvice
public class BusinessExceptionHandler {


    /**
     * 在@ControllerAdvice类中，根据抛出的具体Exception类型匹配@ExceptionHandler中配置的异常类型来匹配错误映射和处理
     */

    public static final String DEFAULT_ERROR_VIEW = "error";

    /**
     * 捕获自定义异常，返回json信息
     */
    @ExceptionHandler(value = BusinessException.class)
    @ResponseBody
    public Result<Object> ErrorHandler(HttpServletRequest rest, BusinessException e) throws BusinessException {

        Result<Object> result = new Result<>();
        result.setCode("404");
        result.setMsg("Not Found BusinessException......");
        result.setUrl(rest.getRequestURL().toString());
        result.setData("");
        return result;
    }

    @ExceptionHandler(value = ArithmeticException.class)
    @ResponseBody
    public Result<Object> ErrorHandler(HttpServletRequest rest, ArithmeticException e) throws BusinessException {
        Result<Object> result = new Result<>();
        result.setCode("405");
        result.setMsg("java.lang.ArithmeticException: / by zero");
        result.setUrl(rest.getRequestURL().toString());
        result.setData("");
        return result;
    }
    @ExceptionHandler(value = Exception.class)
    public ModelAndView ErrorHandler(HttpServletRequest request, Exception e) throws Exception {
        ModelAndView mav = new ModelAndView();
        Result<Exception> error = new Result<>();
        error.setCode("400");
        error.setMsg("系统异常");
        error.setUrl(request.getRequestURI());
        error.setData(e);
        mav.addObject(error);
        mav.setViewName(DEFAULT_ERROR_VIEW);
        return mav;

    }

}
