package com.example.trabalhoDWC.repository;

import com.example.trabalhoDWC.entity.Album;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlbumRepository extends JpaRepository<Album, Long> {}