package com.zdanovich.module_publisher.repository;

import com.zdanovich.module_publisher.model.Issue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IssueRepository extends JpaRepository<Issue, Long> {
    Optional<Issue> findByTitle(String title);
}
