package com.company.service.impl;

import com.common.service.UrlServiceFacade;
import com.company.entity.UrlEntity;
import com.company.repository.UrlEntityMongoDbRepository;
import lombok.AllArgsConstructor;
import org.bson.codecs.ObjectIdGenerator;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class UrlServiceMongoDBFacadeImpl implements UrlServiceFacade {
    private final UrlEntityMongoDbRepository urlEntityMongoDbRepository;

    @Override
    public Mono<String> shortenUrlAndGetHash(String longUrl) {
        return urlEntityMongoDbRepository
                .save(
                        UrlEntity.builder()
                                .id(new ObjectIdGenerator().generate().toString())
                                .longUrl(longUrl)
                                .build()
                )
                .map(x -> String.valueOf(x.getId()));
    }

    @Override
    public Mono<String> getOriginalUrl(String shortUrlHash) {
        return urlEntityMongoDbRepository
                .findById(shortUrlHash)
                .map(UrlEntity::getLongUrl);    }
}
