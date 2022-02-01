package com.example.myapi.repository;

import com.example.myapi.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board, Integer> {

    List<Board> findByDelYnNot(String delYn);

    Optional<Board> findByIdAndDelYn(Integer id, String delYn);
}
