package com.study.jpastudy.chap06_querydsl.repository;

import com.study.jpastudy.chap06_querydsl.entity.Idol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IdolRepository extends JpaRepository<Idol, Long>, IdolCustomRepository{

    @Query("SELECT i FROM Idol i ORDER BY i.age DESC")
    List<Idol> findAllBySorted();
}
