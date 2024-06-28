package com.study.jpastudy.chap06_querydsl.repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.study.jpastudy.chap06_querydsl.entity.Idol;

import java.util.List;

public interface IdolCustomRepository {

    // JPA의 PAGE인터페이스를 사용
    Page<Idol> foundAllByPaging(Pageable pageable);

    // 이름으로 오름차해서 전체조회
    List<Idol> foundAllSortedByName2();

    // 그룹명으로 아이돌을 조회
    List<Idol> foundByGroupName();
}
