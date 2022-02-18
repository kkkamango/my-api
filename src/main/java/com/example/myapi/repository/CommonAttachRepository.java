package com.example.myapi.repository;

import com.example.myapi.model.Board;
import com.example.myapi.model.CommonAttach;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommonAttachRepository extends JpaRepository<CommonAttach, Integer> {

    Optional<CommonAttach> findByIdAndDelYn(Integer id, String delYn);

    List<CommonAttach> findByParentIdAndTypeAndDelYn(Integer parentId, String type, String delYn);
}
