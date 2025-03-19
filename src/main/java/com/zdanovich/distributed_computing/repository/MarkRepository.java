package com.zdanovich.distributed_computing.repository;

import com.zdanovich.distributed_computing.model.Mark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface MarkRepository extends JpaRepository<Mark, Long> {
    Optional<Mark> findByName(String name);

    void deleteByNameIn(List<String> markNames);
}
