package com.example.myapi.repository;

import com.example.myapi.model.Board;
import com.example.myapi.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    List<Category> findByDelYnNotOrderBySortAscModifiedDateDesc(String delYn);

    Optional<Category> findByIdAndDelYn(Integer id, String delYn);
}
