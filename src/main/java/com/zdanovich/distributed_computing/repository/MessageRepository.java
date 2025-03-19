package com.zdanovich.distributed_computing.repository;

import com.zdanovich.distributed_computing.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByIssueId(long issueId);
}
