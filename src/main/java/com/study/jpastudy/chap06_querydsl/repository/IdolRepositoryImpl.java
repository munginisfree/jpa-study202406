package com.study.jpastudy.chap06_querydsl.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.study.jpastudy.chap06_querydsl.entity.Idol;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static com.study.jpastudy.chap06_querydsl.entity.QIdol.*;

@Repository
@RequiredArgsConstructor
public class IdolRepositoryImpl implements IdolCustomRepository {

    private final JdbcTemplate template;
    private final EntityManager em;
    private final JPAQueryFactory factory;

//    // native query 사용
//    public void nativeQuery123(){
//        String sql = "SELECT idol_id, NVL(group_id, '솔로가수') AS g_id" +
//                "FROM tbl_idol I" +
//                "LEFT JOIN tbl_group G" +
//                "ON I.group_id = G.group_id";
//
//        List resultList = em.createNativeQuery(sql)
//                .getResultList();
//    }

    @Override
    public Page<Idol> foundAllByPaging(Pageable pageable) {

        // 페이징을 통한 조회
        List<Idol> idolList = factory
                .selectFrom(idol)
                .orderBy(idol.age.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // 총 조회건수
        Long totalCount = Optional.ofNullable(
                factory
                        .select(idol.count())
                        .from(idol)
                        .fetchOne()
        ).orElse(0L);

        return new PageImpl<>(idolList, pageable, totalCount);
    }

    @Override
    public List<Idol> foundAllSortedByName2() {
        String sql = "SELECT * FROM tbl_idol ORDER BY idol_name ASC";
        return template.query(sql, (rs, n) -> {

            String idolName = rs.getString("idol_name");
            int age = rs.getInt("age");

            return new Idol(
                    idolName,
                    age,
                    null
            );
        });
    }

    @Override
    public List<Idol> foundByGroupName() {
        return factory
                .select(idol)
                .from(idol)
                .orderBy(idol.group.groupName.asc())
                .fetch()
                ;
    }
}
