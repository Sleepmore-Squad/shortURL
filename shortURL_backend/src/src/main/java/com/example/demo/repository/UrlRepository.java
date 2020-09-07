package com.example.demo.repository;

import com.example.demo.entity.Url;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UrlRepository extends JpaRepository<Url, Integer> {
    Url findByShortURL(String shortUrl);

    Optional<Url> findById(Integer id);

    Boolean existsByShortURL(String shortUrl);

}
