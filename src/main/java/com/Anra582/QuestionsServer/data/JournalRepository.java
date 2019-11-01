package com.Anra582.QuestionsServer.data;

import com.Anra582.QuestionsServer.entity.Journal;
import org.springframework.data.repository.CrudRepository;

public interface JournalRepository extends CrudRepository<Journal, String> {
}
