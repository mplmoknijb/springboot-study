package cn.leon.webflux.config;

import cn.leon.webflux.model.Result;
import org.springframework.core.MethodParameter;
import org.springframework.http.codec.HttpMessageWriter;
import org.springframework.web.reactive.HandlerResult;
import org.springframework.web.reactive.accept.RequestedContentTypeResolver;
import org.springframework.web.reactive.result.method.annotation.ResponseBodyResultHandler;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

public class ResponseWrapper extends ResponseBodyResultHandler {
    private static MethodParameter param;

    static {
        try {
            //get new params
            param = new MethodParameter(ResponseWrapper.class
                    .getDeclaredMethod("methodForParams"), -1);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public ResponseWrapper(List<HttpMessageWriter<?>> writers, RequestedContentTypeResolver resolver) {
        super(writers, resolver);
    }

    private static Mono<Result> methodForParams() {
        return null;
    }

    @Override
    public boolean supports(HandlerResult result) {
        boolean isMono = result.getReturnType().resolve() == Mono.class;
        boolean isAlreadyResponse = result.getReturnType().resolveGeneric(0) == Result.class;
        return isMono && !isAlreadyResponse;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Mono<Void> handleResult(ServerWebExchange exchange, HandlerResult result) {
        // modify the result as you want
        Mono<Result<Object>> body = ((Mono<Object>) result.getReturnValue()).map(o -> {
            Result<Object> build = Result.builder()
                    .data(o)
                    .message("success")
                    .code("00000")
                    .build();
            return build;
        });
        return writeBody(body, param, exchange);
    }
}
