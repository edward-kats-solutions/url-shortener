package com.company.service.impl;

import com.common.service.UrlServiceFacade;
import com.company.controller.dto.UrlRequestDTO;
import com.company.service.UrlService;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class UrlServiceImpl implements UrlService {
    private final UrlServiceFacade urlServiceFacade;

    @Override
    public Mono<String> shortenUrlAndGetHash(UrlRequestDTO requestDTO) {
        return urlServiceFacade.shortenUrlAndGetHash(requestDTO.getLongUrl());
    }

    @Override
    @Cacheable(value = "myCache", key = "'url_' + #hash")
    public Mono<String> getOriginalUrl(String hash) {
        return urlServiceFacade
                .getOriginalUrl(hash)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }
}
