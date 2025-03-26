package com.shortty.url_shortener_backend.services;

import com.shortty.url_shortener_backend.dtos.UrlMappingDTO;
import com.shortty.url_shortener_backend.models.UrlMapping;
import com.shortty.url_shortener_backend.models.User;
import com.shortty.url_shortener_backend.repository.UrlMappingRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class UrlMappingService {

    private UrlMappingRepository urlMappingRepository;
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

        UrlMapping urlMapping = new UrlMapping();
        urlMapping.setOriginalUrl(originalUrl);
        urlMapping.setShortUrl(shortUrl);
        urlMapping.setUser(user);
        urlMapping.setCreatedTimeStamp(LocalDateTime.now());
        
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
        urlMappingDTO.setCreatedTimeStamp(urlMapping.getCreatedTimeStamp());

        return urlMappingDTO;
    }


}
