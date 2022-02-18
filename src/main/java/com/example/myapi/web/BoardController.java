package com.example.myapi.web;

import com.example.myapi.model.Board;
import com.example.myapi.service.BoardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@CrossOrigin(origins = "*")
@RestController
@Slf4j
public class BoardController {

    private BoardService boardService;

    @Autowired
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }


    @GetMapping("/board")
    public List<Board> getBoardList(){
//        ResponseEntity
        return boardService.getBoardList();
    }

    @GetMapping("/board/{id}")
    public Board getBoard(@PathVariable("id") Integer id){
        return boardService.getBoard(id);
    }

    @PostMapping("/board")
    public Board saveBoard(@RequestBody Board board){

        log.info("saveBoard requestBody = {}", board);
        return boardService.save(board);
    }

    @PostMapping("/board/{id}")
    public Board updateBoard(@PathVariable("id") Integer id, @RequestBody Board board){

        log.info("updateBoard id = {}", id);
        log.info("updateBoard requestBody = {}", board);

        return boardService.update(id, board);
    }

    @DeleteMapping("/board/{id}")
    public Map deleteBoard(@PathVariable("id") Integer id){

        log.info("deleteBoard id = {}", id);
        Map<String, Object> response = new HashMap<>();
        Integer result = boardService.deleteBoard(id);

        if (result > 0) {
            response.put("result", "OK");
        } else {
            response.put("result", "FAIL");
            response.put("message", "일치하는 정보를 찾을 수 없습니다.");
        }

        return response;
    }
}
