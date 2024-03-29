package cn.leon.exception;

import cn.leon.domain.vo.ResultBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

/**
 * @author mujian
 * @Desc
 * @date 2019/6/30 17:31
 */
@ControllerAdvice
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResultBean ioException(IOException e) {
        log.error("================{}=============", e.getMessage());
        return ResultBean.error(500, "IO异常");
    }
}
