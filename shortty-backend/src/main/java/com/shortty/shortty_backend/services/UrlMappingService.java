package com.shortty.shortty_backend.services;

import com.shortty.shortty_backend.dtos.ClickEventDTO;
import com.shortty.shortty_backend.dtos.UrlMappingDTO;
import com.shortty.shortty_backend.models.ClickEvent;
import com.shortty.shortty_backend.models.UrlMapping;
import com.shortty.shortty_backend.models.User;
import com.shortty.shortty_backend.repository.ClickEventRepository;
import com.shortty.shortty_backend.repository.UrlMappingRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UrlMappingService {

    private UrlMappingRepository urlMappingRepository;
    private ClickEventRepository clickEventRepository;
    private static final String BASE62_ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    public static String generateShortUrl(String originalUrl) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = digest.digest(originalUrl.getBytes());

        // Convert to Base62 and return first 8 characters
        return base62Encode(new BigInteger(1, hashBytes)).substring(0, 12);
    }

    private static String base62Encode(BigInteger value) {
        StringBuilder encoded = new StringBuilder();
        BigInteger base = BigInteger.valueOf(62);
        while (value.compareTo(BigInteger.ZERO) > 0) {
            encoded.append(BASE62_ALPHABET.charAt(value.mod(base).intValue()));
            value = value.divide(base);
        }
        return encoded.reverse().toString();
    }

    public UrlMappingDTO createShortUrl(String originalUrl, User user) throws NoSuchAlgorithmException {
        String shortUrl = generateShortUrl(originalUrl);

        boolean isThisShortUrlOwnedByThisUser = urlMappingRepository.existsByUserAndShortUrl(user, shortUrl);
        if (isThisShortUrlOwnedByThisUser)  return null;

        UrlMapping urlMapping = new UrlMapping();
        urlMapping.setOriginalUrl(originalUrl);
        urlMapping.setShortUrl(shortUrl);
        urlMapping.setUser(user);
        urlMapping.setCreatedAt(LocalDateTime.now());
        
        UrlMapping savedUrlMapping = urlMappingRepository.save(urlMapping);
        return convertToUrlMappingDTO(savedUrlMapping);
    }

    private UrlMappingDTO convertToUrlMappingDTO(UrlMapping urlMapping) {
        UrlMappingDTO urlMappingDTO = new UrlMappingDTO();

        urlMappingDTO.setId(urlMapping.getId());
        urlMappingDTO.setOriginalUrl(urlMapping.getOriginalUrl());
        urlMappingDTO.setShortUrl(urlMapping.getShortUrl());
        urlMappingDTO.setUsername(urlMapping.getUser().getUsername());
        urlMappingDTO.setClickCount(urlMapping.getClickCount());
        urlMappingDTO.setCreatedAt(urlMapping.getCreatedAt());

        return urlMappingDTO;
    }

    public List<UrlMappingDTO> getUserUrls(User user) {
        return urlMappingRepository.findByUser(user)
                .stream()
                .map(this::convertToUrlMappingDTO)
                .toList();
    }

    public List<ClickEventDTO> getClickEventsByDate(User user, String shortUrl, LocalDateTime startAt, LocalDateTime endAt) {
        UrlMapping urlMapping = urlMappingRepository.findByUserAndShortUrl(user, shortUrl);
        if (urlMapping == null) return new ArrayList<>();

        return clickEventRepository.findByUrlMappingAndClickedAtBetween(urlMapping, startAt, endAt)
                .stream()
                .collect(Collectors.groupingBy(clickEvent -> clickEvent.getClickedAt().toLocalDate(),Collectors.counting()))
                .entrySet().stream()
                .map(entry -> {
                    ClickEventDTO clickEventDTO = new ClickEventDTO();
                    clickEventDTO.setClickedAt(entry.getKey());
                    clickEventDTO.setCount(entry.getValue());
                    return clickEventDTO;
                })
                .collect(Collectors.toList());
    }

    public Map<LocalDate, Long> getTotalClicksByUserAndDate(User user, LocalDate start, LocalDate end) {
        List<UrlMapping> urlMappings = urlMappingRepository.findByUser(user);
        return clickEventRepository.findByUrlMappingInAndClickedAtBetween(urlMappings, start.atStartOfDay(), end.plusDays(1).atStartOfDay())
                .stream()
                .collect(Collectors.groupingBy(clickEvent -> clickEvent.getClickedAt().toLocalDate(), Collectors.counting()));
    }

    public UrlMapping getUrlMappingFromShortUrl(String shortUrl) {
        List<UrlMapping> urlMappings = urlMappingRepository.findByShortUrl(shortUrl);
        if (urlMappings == null) {
            return null;
        }
        // increment the click count
        urlMappings.forEach(urlMapping -> urlMapping.setClickCount(urlMapping.getClickCount() + 1));
        urlMappingRepository.saveAll(urlMappings);

        // record the click event
        List<ClickEvent> clickEvents = urlMappings.stream()
                .map(urlMapping -> {
                    ClickEvent clickEvent = new ClickEvent();
                    clickEvent.setUrlMapping(urlMapping);
                    clickEvent.setClickedAt(LocalDateTime.now());
                    return clickEvent;
                })
                .toList();

        clickEventRepository.saveAll(clickEvents);

        return urlMappings.getFirst();
    }
}
