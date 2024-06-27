package com.study.jpastudy.chap05.entity;

import lombok.*;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;
@Setter @Getter
@ToString(exclude = "purchaseList")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tbl_mtm_goods")
public class Goods {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "goods_id")
    private Long id;

    @Column(name = "goods_name")
    private String name;

    @OneToMany(mappedBy = "goods", orphanRemoval = true, cascade = CascadeType.ALL)
    @Builder.Default
    private List<Purchase> purchaseList = new ArrayList<>();
}
