package com.company.service.impl;

import com.company.controller.dto.UrlRequestDTO;
import com.company.entity.UrlEntity;
import com.company.repository.UrlRepository;
import com.company.service.UrlService;
import lombok.AllArgsConstructor;
import org.bson.codecs.ObjectIdGenerator;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@AllArgsConstructor
public class UrlServiceImpl implements UrlService {
    private final UrlRepository urlRepository;

    @Override
    public Mono<String> shortenUrlAndGetHash(UrlRequestDTO requestDTO) {
        var urlMono = urlRepository.save(
                UrlEntity.builder()
                        .id(new ObjectIdGenerator().generate().toString())
                        .longUrl(requestDTO.getLongUrl())
                        .build()
        );
        return urlMono.map(x -> String.valueOf(x.getId()));
    }

    @Override
    @Cacheable(value = "myCache", key = "'url_' + #hash")
    public Mono<String> getOriginalUrl(String hash) {
        return urlRepository
                .findById(hash)
                .map(UrlEntity::getLongUrl)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }
}
