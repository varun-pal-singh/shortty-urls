package com.shortty.backend.controller;

import com.shortty.backend.models.UrlMapping;
import com.shortty.backend.services.UrlMappingService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class RedirectController {

    private UrlMappingService urlMappingService;

    @GetMapping("/{shortUrl}")
    public ResponseEntity<Void> redirect(@PathVariable String shortUrl) {
        UrlMapping urlMapping = urlMappingService.getUrlMappingFromShortUrl(shortUrl);
        if (urlMapping == null) return ResponseEntity.notFound().build();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Location", urlMapping.getOriginalUrl());
        return ResponseEntity.status(302).headers(httpHeaders).build();
    }

}
