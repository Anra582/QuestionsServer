package com.Anra582.QuestionsServer.data;

import com.Anra582.QuestionsServer.entity.Session;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SessionRepository extends CrudRepository<Session, Long> {

    List<Session> findByFioContainingIgnoreCase(String searchString);
}
