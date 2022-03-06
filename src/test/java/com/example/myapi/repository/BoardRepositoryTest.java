package com.example.myapi.repository;

import com.example.myapi.model.Board;
import com.example.myapi.model.Category;
import com.example.myapi.model.CommonAttach;
import com.example.myapi.model.CommonAttachType;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@Slf4j
class BoardRepositoryTest {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CommonAttachRepository commonAttachRepository;

    @Test
    public void 게시물_등록_테스트(){

        Category category = Category.builder()
                .name("디자인")
                .sort(1)
                .description("카테고리 등록 테스트")
                .build();

        log.info("카테고리 ={}", category);
        categoryRepository.save(category);

        Board board = Board.builder()
                .category(category)
                .state("T1")
                .subject("디자인 작업 해드립니다.")
                .amount(5000)
                .build();
        board.setDelYn("N");
        log.info("board ={}", board);
        board = boardRepository.save(board);

        // 메인이미지
        CommonAttach commonAttach = CommonAttach.builder()
                .fileName("파일이름.txt")
                .filePath("파일경로")
//                .parentId(board.getId())
                .type(CommonAttachType.BOARD_IMG_MAIN)
                .build();

        commonAttachRepository.save(commonAttach);
        board.setImgSrcMain(commonAttach);
        log.info("MAIN 파일 저장 {}", board);

        // 상세이미지
        CommonAttach commonAttachDetail1 = CommonAttach.builder()
                .fileName("상세파일이름.txt")
                .filePath("상세파일경로")
                .type(CommonAttachType.BOARD_IMG_DETAIL)
                .build();
        commonAttachRepository.save(commonAttachDetail1);

        CommonAttach commonAttachDetail2 = CommonAttach.builder()
                .fileName("상세파일이름2.txt")
                .filePath("상세파일경로2")
                .type(CommonAttachType.BOARD_IMG_DETAIL)
                .build();
        commonAttachRepository.save(commonAttachDetail2);

        List<CommonAttach> detailList = new ArrayList<>();
        detailList.add(commonAttachDetail1);
        detailList.add(commonAttachDetail2);

        board.setImgSrcDetail(detailList);
        log.info("DETAIL 파일 저장 {}", board);

//        boardRepository.save(board);

        List<CommonAttach> all = commonAttachRepository.findAll();
        log.info("저장된 CommonAttach 조회 {}", all);

        Optional<Board> optionalBoard = boardRepository.findByIdAndDelYn(board.getId(), "N");
        log.info("저장된 board 조회 {}", optionalBoard.get());

//        boardRepository.deleteAll();
//        categoryRepository.deleteAll();
    }
}