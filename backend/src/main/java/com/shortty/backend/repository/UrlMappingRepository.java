package com.shortty.backend.repository;

import com.shortty.backend.models.UrlMapping;
import com.shortty.backend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UrlMappingRepository extends JpaRepository<UrlMapping, Long> {

    boolean existsByUserAndShortUrl(User user, String shortUrl);
    UrlMapping findByUserAndShortUrl(User user, String shortUrl);
    List<UrlMapping> findByShortUrl(String shortUrl);
    List<UrlMapping> findByUser(User user);
}
