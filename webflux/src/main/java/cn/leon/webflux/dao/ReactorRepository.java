package cn.leon.webflux.dao;

import cn.leon.webflux.entity.ReactorEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface ReactorRepository extends ReactiveCrudRepository<ReactorEntity, String> {

    @Query("SELECT * FROM reactor WHERE last_name = :lastname")
    Flux<ReactorEntity> findByLastName(String lastName);

}
