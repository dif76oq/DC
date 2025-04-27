package com.zdanovich.module_publisher.repository;

import com.zdanovich.module_publisher.model.Mark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MarkRepository extends JpaRepository<Mark, Long> {
    Optional<Mark> findByName(String name);

    void deleteByNameIn(List<String> markNames);
}
