package com.Anra582.QuestionsServer.data;

import com.Anra582.QuestionsServer.entity.Answer;
import com.Anra582.QuestionsServer.entity.Question;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface AnswerRepository extends CrudRepository<Answer, Long> {
    List<Answer> findByQuestion(Question question);
}
