package com.shortty.url_shortener_backend.repository;

import com.shortty.url_shortener_backend.models.ClickEvent;
import com.shortty.url_shortener_backend.models.UrlMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ClickEventRepository extends JpaRepository<ClickEvent, Long> {
    List<ClickEvent> findByUrlMappingAndClickedAtBetween (UrlMapping urlMapping, LocalDateTime startAt, LocalDateTime endAt);
    List<ClickEvent> findByUrlMappingInAndClickedAtBetween (List<UrlMapping> urlMappings, LocalDateTime startAt, LocalDateTime endAt);
}
