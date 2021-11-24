package cn.leon.webflux.service;

import cn.leon.webflux.dao.ReactorRepository;
import cn.leon.webflux.model.TestDTO;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

@Slf4j
@Service
public class WebClientService {

    private WebClient webClient;

    public WebClientService() {
        // netty
        HttpClient httpClient = HttpClient
                .create()
                .wiretap(true);
        ClientHttpConnector reactorClientHttpConnector = new ReactorClientHttpConnector(httpClient);
        // webflux client
        WebClient.Builder builder = WebClient.builder()
                .baseUrl("http://localhost:8080")
                // netty
                .clientConnector(reactorClientHttpConnector)
                // filter
                .filter(ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
//                    log.info("Request[url:{},method:{},body:{},headers:{}", clientRequest.url(), clientRequest.method(), clientRequest.body(), clientRequest.attributes());
                    return Mono.just(clientRequest);
                }))
                .filter(ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
                    return Mono.just(clientResponse);
                }));
        // codecs
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        builder.codecs(clientCodecConfigurer -> {
            // Provides a way to customize or replace HTTP message readers and writers registered by default.
            clientCodecConfigurer.defaultCodecs().jackson2JsonDecoder(new Jackson2JsonDecoder(objectMapper));
            // Register custom HTTP message readers or writers in addition to the ones registered by default.
//                    clientCodecConfigurer.customCodecs().register();
        });
//                .exchangeStrategies()
        webClient = builder.build();
    }

    @Autowired
    private ReactorRepository reactorRepository;

    public Mono<TestDTO> invoke() {

//        return reactorRepository.save(ReactorEntity.builder().lastName("demo")
//                .name("test")
//                .build())
//                .flatMap(entity -> reactorRepository.findByLastName("demo")
//                        .flatMap(reactorEntity -> {
//                            log.info("id: {}, name: {}, last-name: {}", reactorEntity.getId(), reactorEntity.getName(), reactorEntity.getLastName());
//                            return Mono.empty();
//                        }));
        return reactorRepository.findByLastName("demo")
                .collectList()
                .flatMap(entityList -> {
                    entityList.forEach(reactorEntity -> {
                        log.info("id: {}, name: {}, last-name: {}", reactorEntity.getId(), reactorEntity.getName(), reactorEntity.getLastName());
                    });
                    return Mono.empty();
                });

//        return webClient
//                .post()
//                .uri("/v1/alpha")
//                .accept(MediaType.APPLICATION_JSON)
//                .retrieve()
//                .bodyToMono(TestDTO.class);
    }
}
