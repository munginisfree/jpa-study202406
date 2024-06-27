package com.study.jpastudy.chap06_querydsl.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.study.jpastudy.chap06_querydsl.entity.Group;
import com.study.jpastudy.chap06_querydsl.entity.Idol;
import com.study.jpastudy.chap06_querydsl.entity.QIdol;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

import static com.study.jpastudy.chap06_querydsl.entity.QIdol.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
//@Rollback(false)
class QueryDslBasicTest {
    @Autowired
    IdolRepository idolRepository;
    @Autowired
    GroupRepository groupRepository;

    // JPA의 CRUD를 제어하는 객체
    @Autowired
    EntityManager em;

    @Autowired
    JPAQueryFactory factory;

    @BeforeEach
    void setUp(){
        Group leSserafim = new Group("르세라핌");
        Group ive = new Group("아이브");

        groupRepository.save(leSserafim);
        groupRepository.save(ive);

        Idol idol1 = new Idol("김채원", 24, leSserafim);
        Idol idol2 = new Idol("사쿠라", 26, leSserafim);
        Idol idol3 = new Idol("가을", 22, ive);
        Idol idol4 = new Idol("리즈", 20, ive);

        idolRepository.save(idol1);
        idolRepository.save(idol2);
        idolRepository.save(idol3);
        Idol save = idolRepository.save(idol4);

    }

    @Test
    @DisplayName("JPQL로 특정이름의 아이돌 조회하기")
    void jpqlTest() {
        //given
        String jpqlQuery = "SELECT i FROM Idol i WHERE i.idolName = ?1";
        //when
        Idol foundIdol = em.createQuery(jpqlQuery, Idol.class)
                .setParameter(1, "가을")
                .getSingleResult();
        //then
        assertEquals("아이브", foundIdol.getGroup().getGroupName());

        System.out.println("\n\n\n\n");
        System.out.println("foundIdol = " + foundIdol);
        System.out.println("foundIdol.getGroup() = " + foundIdol.getGroup());
        System.out.println("\n\n\n\n");
    }

    @Test
    @DisplayName("QueryDSL로 특정이름의 아이돌 조회하기")
    void queryDslTest() {
        //given
        // QueryDsl로 JPQL을 만드는 빌더
//        JPAQueryFactory factory = new JPAQueryFactory(em);
        //when
        Idol foundIdol = factory
                .select(idol)
                .from(idol)
                .where(idol.idolName.eq("사쿠라"))
                .fetchOne();
        //then
        assertEquals("르세라핌", foundIdol.getGroup().getGroupName());

        System.out.println("\n\n\n\n");
        System.out.println("foundIdol = " + foundIdol);
        System.out.println("foundIdol.getGroup() = " + foundIdol.getGroup());
        System.out.println("\n\n\n\n");
    }

    @Test
    @DisplayName("이름과 나이로 아이돌 조회하기")
    void searchTest() {
        //given
        String name = "리즈";
        int age = 20;
        //when
        Idol idol1 = factory
                .select(idol)
                .from(idol)
                .where(idol.idolName.eq(name)
                        .and(idol.age.eq(age)))
                .fetchOne();
        //then
        assertEquals("아이브", idol1.getGroup().getGroupName());
    }

    @Test
    @DisplayName("조회 결과 반환하기")
    void fetchTest() {
        // 리스트 조회
        List<Idol> idolList = factory
                .select(idol)
                .from(idol)
                .fetch();
        System.out.println("\n\n=========================================");
        System.out.println("idolList = " + idolList);
        System.out.println("=========================================\n\n");

        // 단건 조회시 null safety를 위한 Optional로 받고 싶을 때
        Optional<Idol> foundIdolOptional = Optional.ofNullable(factory
                .select(QIdol.idol)
                .from(QIdol.idol)
                .where(QIdol.idol.age.lt(21))
                .fetchOne());

        Idol idol2 = foundIdolOptional.orElseThrow();
        System.out.println("\n\n=========================================");
        System.out.println("idol = " + idol2);
        System.out.println("=========================================\n\n");
    }
}