package com.company.service;

import com.company.controller.dto.UrlRequestDTO;
import reactor.core.publisher.Mono;

public interface UrlService {

    Mono<String> shortenUrlAndGetHash(UrlRequestDTO requestDTO);
    Mono<String> getOriginalUrl(String shortUrlHash);
}
