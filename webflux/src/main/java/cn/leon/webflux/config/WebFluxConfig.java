package cn.leon.webflux.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.accept.RequestedContentTypeResolver;

@Configuration
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
}
