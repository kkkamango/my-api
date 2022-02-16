package com.example.myapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Board extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String subject; // 서비스 제목
    private String contents; // 설명
    private Integer amount; // 금액(VAT포함) - 최소 5,000원 이상
    private String term; // 작업기간 0일 (1d, 2d,...,1m, 1y)
    private Integer revisionNo; // 수정횟수
    private String revisionNoti; // 수정 및 재진행 안내

    @OneToOne
    @JoinColumn(name="imgSrcMainId")
    private CommonAttach imgSrcMain; // 메인 이미지 -> 가이드 라인 type=BOARD_IMG_MAIN
    @OneToMany
    @JoinColumn(name = "imgSrcDetailId")
    private Collection<CommonAttach> imgSrcDetail; // 상세 이미지 3~9개 type=BOARD_IMG_DETAIL
    @OneToMany
    @JoinColumn(name = "vedioSrcId", nullable = true)
    private Collection<CommonAttach> vedioSrc; // 동영상 ~6개 type=BOARD_VEDIO
    // 작업전 요청 사항


    @ManyToOne
    @JoinColumn(name= "categoryId")
    private Category category; // 카테고리

}
