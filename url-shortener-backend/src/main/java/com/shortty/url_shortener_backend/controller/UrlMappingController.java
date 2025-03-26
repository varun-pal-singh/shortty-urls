package com.shortty.url_shortener_backend.controller;

import com.shortty.url_shortener_backend.dtos.UrlMappingDTO;
import com.shortty.url_shortener_backend.models.User;
import com.shortty.url_shortener_backend.services.UrlMappingService;
import com.shortty.url_shortener_backend.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping("/api/urls")
@AllArgsConstructor
public class UrlMappingController {
    private UrlMappingService urlMappingService;
    private UserService userService;


    // { "originalUrl" : "https://www.google.com" }
    @PostMapping("/shorten")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<UrlMappingDTO> shortenUrl(@RequestBody Map<String, String> request,
                                                    Principal principal) {
        try {
            String originalUrl = request.get("originalUrl");
            User user = userService.getUser(principal.getName());

            UrlMappingDTO urlMappingDTO = urlMappingService.createShortUrl(originalUrl, user);
            if (urlMappingDTO == null || urlMappingDTO.isEmpty()) {
                new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(urlMappingDTO, HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
