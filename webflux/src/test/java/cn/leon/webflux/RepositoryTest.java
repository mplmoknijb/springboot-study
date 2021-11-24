package cn.leon.webflux;

import cn.leon.webflux.dao.ReactorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.test.StepVerifier;

@ExtendWith(SpringExtension.class)
@ContextConfiguration
class RepositoryTest {

    @Autowired
    private ReactorRepository repository;

    @Test
    void readsAllEntitiesCorrectly() {

        repository.findByLastName("3")
                .as(StepVerifier::create)
                .expectNextCount(1)
                .verifyComplete();
    }
}