//package cn.leon.webflux.config;
//
//import cn.leon.webflux.util.ReactiveRequestContextHolder;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.annotation.Order;
//import org.springframework.http.server.reactive.ServerHttpRequest;
//import org.springframework.web.reactive.HandlerMapping;
//import org.springframework.web.reactive.result.method.annotation.RequestMappingHandlerMapping;
//import org.springframework.web.server.ServerWebExchange;
//import org.springframework.web.server.WebFilter;
//import org.springframework.web.server.WebFilterChain;
//import reactor.core.publisher.Mono;
//
//@Slf4j
//@Order(1)
//@Configuration
//@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
//public class WebFluxFilter implements WebFilter {
//
//    @Autowired
//    private RequestMappingHandlerMapping requestMappingHandlerMapping;
//
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
//        ServerHttpRequest request = exchange.getRequest();
//        requestMappingHandlerMapping.getHandler(exchange);
//        String path = exchange.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
//        String bestMatchPattern = exchange.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE).toString();
//        Object attribute = exchange.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
////        log.info("=========1==========");
//        return chain.filter(exchange)
//                .subscriberContext(ctx -> ctx.put(ReactiveRequestContextHolder.CONTEXT_KEY, request));
//    }
//}
