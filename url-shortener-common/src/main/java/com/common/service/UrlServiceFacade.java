package com.common.service;

import reactor.core.publisher.Mono;

public interface UrlServiceFacade {

    Mono<String> shortenUrlAndGetHash(String longUrl);
    Mono<String> getOriginalUrl(String shortUrlHash);
}
