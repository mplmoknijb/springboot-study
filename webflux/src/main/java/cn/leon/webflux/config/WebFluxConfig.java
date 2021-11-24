package cn.leon.webflux.config;

import cn.leon.webflux.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.accept.RequestedContentTypeResolver;

@Slf4j
@Configuration
@RestControllerAdvice
public class WebFluxConfig {

    @Autowired
    private ServerCodecConfigurer serverCodecConfigurer;
    @Autowired
    private RequestedContentTypeResolver requestedContentTypeResolver;

    @Bean
    public ResponseWrapper responseWrapper() {
        return new ResponseWrapper(serverCodecConfigurer
                .getWriters(), requestedContentTypeResolver);
    }

    @ExceptionHandler(Exception.class)
    public Result handleException(Exception ex) {
        log.error("ex: {}", ex.getMessage());
        return Result.builder()
                .code("00001")
                .message(ex.getMessage())
                .build();
    }
}
