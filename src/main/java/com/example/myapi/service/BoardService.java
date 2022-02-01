package com.example.myapi.service;

import com.example.myapi.model.Board;
import com.example.myapi.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class BoardService {

    private BoardRepository boardRepository;

    @Autowired
    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public List<Board> getBoardList(){
        return boardRepository.findByDelYnNot("Y");
    }

    public Board getBoard(Integer id){
        return boardRepository.findByIdAndDelYn(id, "N").orElseGet(Board::new);
    }

    public Board save(Board board) {
        Board savedBoad = boardRepository.save(board);
        return savedBoad;
    }

    public Board update(int id, Board board) {

        board.setId(id);
        board.setDelYn("N");
        return this.save(board);
    }

    @Transactional
    public Integer deleteBoard(Integer id) {

        Optional<Board> optionalBoard = boardRepository.findByIdAndDelYn(id, "N");

        if (optionalBoard.isPresent()){
            Board board = optionalBoard.get();
            board.setDelYn("Y");
            boardRepository.save(board);
            return board.getId();
        }
        return 0;
    }

}
