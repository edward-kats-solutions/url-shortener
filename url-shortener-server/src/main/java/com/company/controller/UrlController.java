package com.company.controller;

import com.company.controller.dto.UrlRequestDTO;
import com.company.controller.dto.UrlResponseDTO;
import com.company.service.UrlService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.view.RedirectView;
import reactor.core.publisher.Mono;

import java.net.URI;

@Log4j2
@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class UrlController {

    private static final String DOMAIN_PREFIX = "http://localhost:8080/api/v1/";

    private final UrlService urlService;

    @PostMapping("/shorten")
    public Mono<UrlResponseDTO> shortenUrl(@RequestBody UrlRequestDTO urlRequestDTO) {
        return urlService
                .shortenUrlAndGetHash(urlRequestDTO)
                .map(hash -> DOMAIN_PREFIX + hash)
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
                    log.info("Redirect: short url: {}, long url: {}", DOMAIN_PREFIX + hash, url);
                    return ResponseEntity
                            .status(HttpStatus.FOUND)
                            .location(URI.create(url))
                            .build();
                })
                ;
    }
}
