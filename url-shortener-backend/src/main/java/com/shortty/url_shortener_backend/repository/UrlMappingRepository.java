package com.shortty.url_shortener_backend.repository;

import com.shortty.url_shortener_backend.models.UrlMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlMappingRepository extends JpaRepository<UrlMapping, Long> {

}
