package com.company.controller;

import com.company.controller.dto.UrlRequestDTO;
import com.company.controller.dto.UrlResponseDTO;
import com.company.service.UrlService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.net.URI;

@Log4j2
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UrlController {

    @Value("${hostname}")
    private String hostname;

    @Value("${hostname-protocol}")
    private String hostNameProtocol;

    private final UrlService urlService;

    @PostMapping("/shorten")
    public Mono<UrlResponseDTO> shortenUrl(@RequestBody UrlRequestDTO urlRequestDTO) {
        return urlService
                .shortenUrlAndGetHash(urlRequestDTO)
                .map(hash -> getDomainPrefix() + hash)
                .map(responseUrl -> {
                    log.info("ShortenURL: Long url: {}, short url: {}", urlRequestDTO.getLongUrl(), responseUrl);
                    return UrlResponseDTO.builder()
                            .shortUrl(responseUrl)
                            .build();
                });
    }

    @GetMapping("/{hash}")
    public Mono<ResponseEntity<Void>> abc(@PathVariable("hash") String hash) {
        return urlService.getOriginalUrl(hash)
                .map(url -> {
                    log.info("Redirect: short url: {}, long url: {}", getDomainPrefix() + hash, url);
                    return ResponseEntity
                            .status(HttpStatus.FOUND)
                            .location(URI.create(url))
                            .build();
                })
                ;
    }

    private String getDomainPrefix() {
        return this.hostNameProtocol + this.hostname + "/api/v1/";
    }
}
