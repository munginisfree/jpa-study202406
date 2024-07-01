package com.study.jpastudy.chap06_querydsl.repository;

import com.study.jpastudy.chap06_querydsl.entity.Album;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlbumRepository extends JpaRepository<Album, Long> {
}
