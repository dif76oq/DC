package com.zdanovich.module_publisher.repository;

import com.zdanovich.module_publisher.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByIssueId(long issueId);
}
