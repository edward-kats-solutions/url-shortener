package com.company.service.impl;

import com.common.service.UrlServiceFacade;
import com.company.entity.UrlEntity;
import com.company.repository.UrlEntityCassandraRepository;
import lombok.AllArgsConstructor;
import org.bson.codecs.ObjectIdGenerator;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class UrlServiceCassandraFacadeImpl implements UrlServiceFacade {
    private final UrlEntityCassandraRepository urlEntityCassandraRepository;

    @Override
    public Mono<String> shortenUrlAndGetHash(String longUrl) {
        return urlEntityCassandraRepository
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
        return urlEntityCassandraRepository
                .findById(shortUrlHash)
                .map(UrlEntity::getLongUrl);
    }
}
