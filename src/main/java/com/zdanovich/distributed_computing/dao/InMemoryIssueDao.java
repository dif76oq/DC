package com.zdanovich.distributed_computing.dao;

import com.zdanovich.distributed_computing.model.Issue;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class InMemoryIssueDao {
    private final Map<Long, Issue> issues = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public Issue save(Issue issue) {
        if (issue.getId()==0) {
            issue.setId(idGenerator.getAndIncrement());
        }
        issues.put(issue.getId(), issue);
        return issue;
    }

    public List<Issue> findAll() {
        return new ArrayList<>(issues.values());
    }
    public Optional<Issue> findById(long id) {
        return Optional.ofNullable(issues.get(id));
    }

    public void deleteById(long id) {
        issues.remove(id);
    }
}
