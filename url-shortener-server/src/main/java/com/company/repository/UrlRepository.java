package com.company.repository;

import com.company.entity.UrlEntity;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import org.springframework.lang.NonNullApi;
import reactor.core.publisher.Mono;

import javax.annotation.Nonnull;
import java.util.UUID;

public interface UrlRepository extends ReactiveCassandraRepository<UrlEntity, String> {

    @Nonnull
    @AllowFiltering()
    @Override
    Mono<UrlEntity> findById(@Nonnull String id);
}
