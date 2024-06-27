package com.study.jpastudy.chap05.repository;

import com.study.jpastudy.chap05.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
