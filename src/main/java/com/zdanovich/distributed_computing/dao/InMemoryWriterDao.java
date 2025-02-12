package com.zdanovich.distributed_computing.dao;

import com.zdanovich.distributed_computing.model.Writer;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class InMemoryWriterDao {
    private final Map<Long, Writer> writers = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public Writer save(Writer writer) {
        if (writer.getId()==0) {
            writer.setId(idGenerator.getAndIncrement());
        }
        writers.put(writer.getId(), writer);
        return writer;
    }

    public List<Writer> findAll() {
        return new ArrayList<>(writers.values());
    }
    public Optional<Writer> findById(long id) {
        return Optional.ofNullable(writers.get(id));
    }
    public Optional<Writer> findByLogin(String login) {
        return writers.values()
                .stream()
                .filter(writer -> writer.getLogin().equals(login))
                .findFirst();
    }


    public void deleteById(long id) {
        writers.remove(id);
    }
}
