package com.shortty.shortty_backend.controller;

import com.shortty.shortty_backend.dtos.ClickEventDTO;
import com.shortty.shortty_backend.dtos.UrlMappingDTO;
import com.shortty.shortty_backend.models.User;
import com.shortty.shortty_backend.services.UrlMappingService;
import com.shortty.shortty_backend.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
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
            if (urlMappingDTO == null || urlMappingDTO.getShortUrl().isEmpty()) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(urlMappingDTO, HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/myurls")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<UrlMappingDTO>> getUserUrls(Principal principal) {
        User user = userService.getUser(principal.getName());
        List<UrlMappingDTO> userUrls = urlMappingService.getUserUrls(user);
        if (userUrls.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(userUrls, HttpStatus.OK);
    }

    // /analytics/{shortUrl}?startAt=2025-01-01T00:00:03&endAt=2025-01-02T01:20:01
    @GetMapping("/analytics/{shortUrl}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<ClickEventDTO>> getUrlAnalytics (Principal principal,
                                                                @PathVariable String shortUrl,
                                                                @RequestParam("startAt") String startAt,
                                                                @RequestParam("endAt") String endAt) {
        User user = userService.getUser(principal.getName());
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        LocalDateTime start = LocalDateTime.parse(startAt, formatter);
        LocalDateTime end = LocalDateTime.parse(endAt, formatter);
        List<ClickEventDTO> clickEventDTOs = urlMappingService.getClickEventsByDate(user, shortUrl, start, end);
        return ResponseEntity.ok(clickEventDTOs);
    }

    // /totalClicks?startAt=2025-01-01T00:00:03&endAt=2025-01-02T01:20:01
    @GetMapping("/totalClicks")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Map<LocalDate, Long>> getTotalClicksByDate (Principal principal,
                                                                      @RequestParam("startAt") String startAt,
                                                                      @RequestParam("endAt") String endAt) {
        User user = userService.getUser(principal.getName());
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        LocalDate start = LocalDate.parse(startAt, formatter);
        LocalDate end = LocalDate.parse(endAt, formatter);

        Map<LocalDate, Long> totalClicks = urlMappingService.getTotalClicksByUserAndDate(user, start, end);
        return ResponseEntity.ok(totalClicks);
    }
}
