package com.study.jpastudy.chap05.repository;

import com.study.jpastudy.chap05.entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
}
