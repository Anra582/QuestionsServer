package com.Anra582.QuestionsServer.data;

import com.Anra582.QuestionsServer.entity.Question;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface QuestionRepository extends CrudRepository<Question, Long> {

    List<Question> findByNameContainingIgnoreCase(String searchString);
}
