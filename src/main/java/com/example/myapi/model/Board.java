package com.example.myapi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Board extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String subject; // 서비스 제목
    private String state; // 서비스 등록 상태 (T01, T02, ON)
    private String contents; // 설명
    private Integer amount; // 금액(VAT포함) - 최소 5,000원 이상
    private String term; // 작업기간 0일 (TODO CommonTermType 1d, 2d,...,1m, 1y)
    private Integer revisionNo; // 수정횟수
    // @Lob @Basic(fetch = FetchType.LAZY)
    private String revisionNoti; // 수정 및 재진행 안내

    @OneToOne(fetch = FetchType.EAGER)
    // board.img_src_main_id 컬럼 생성
    private CommonAttach imgSrcMain; // 메인 이미지 -> 가이드 라인 type=BOARD_IMG_MAIN
    @OneToMany(fetch = FetchType.LAZY)
    // board_img_src_detail 매핑테이블 생성
    private Collection<CommonAttach> imgSrcDetail; // 상세 이미지 3~9개 type=BOARD_IMG_DETAIL
    @OneToMany(fetch = FetchType.LAZY)
    // board_video_src 매핑테이블 생성
    private Collection<CommonAttach> videoSrc; // 동영상 ~6개 type=BOARD_VIDEO
    // 작업전 요청 사항


    @ManyToOne
    @JoinColumn(name= "categoryId")
    private Category category; // 카테고리

}
