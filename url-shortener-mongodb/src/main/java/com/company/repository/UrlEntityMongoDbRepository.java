package com.company.repository;

import com.company.entity.UrlEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface UrlEntityMongoDbRepository extends ReactiveMongoRepository<UrlEntity, String> {
}
