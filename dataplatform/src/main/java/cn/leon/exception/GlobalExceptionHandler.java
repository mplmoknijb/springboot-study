package cn.leon.exception;

import java.io.IOException;

import org.elasticsearch.ElasticsearchStatusException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.leon.domain.vo.ResultBean;
import lombok.extern.slf4j.Slf4j;

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

    @ExceptionHandler
    public ResultBean esException(ElasticsearchStatusException e) {
        log.error("================{}==============", e.getMessage());
        return ResultBean.error(500, "索引异常");
    }
}
