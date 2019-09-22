package com.Anra582.QuestionsServer.service;

import com.Anra582.QuestionsServer.controller.dto.AnswerItemDTO;
import com.Anra582.QuestionsServer.controller.dto.QuestionItemDTO;
import com.Anra582.QuestionsServer.data.AnswerRepository;
import com.Anra582.QuestionsServer.data.QuestionRepository;
import com.Anra582.QuestionsServer.entity.Answer;
import com.Anra582.QuestionsServer.entity.Question;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    public QuestionServiceImpl(QuestionRepository questionRepository, AnswerRepository answerRepository) {
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
    }

    @Override
    public QuestionItemDTO createQuestion(QuestionItemDTO questionItemDTO) {
        Question question = new Question();
        question.setName(questionItemDTO.name);
        questionRepository.save(question);

        saveAnswers(questionItemDTO.answers, question);

        return new QuestionItemDTO(question, answerRepository.findByQuestion(question));
    }

    @Override
    public QuestionItemDTO editQuestion(QuestionItemDTO questionItemDTO) {

        Question question = getQuestionById(Long.valueOf(questionItemDTO.id));
        question.setName(questionItemDTO.name);
        questionRepository.save(question);

        answerRepository.findByQuestion(question).forEach(answer -> answerRepository.deleteById(answer.getId()));

        saveAnswers(questionItemDTO.answers, question);

        return new QuestionItemDTO(question, answerRepository.findByQuestion(question));
    }

    private Question getQuestionById(Long id) {
        return questionRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException(String.format("Cannot find Question by id = %s", id)));
    }

    private void saveAnswers(List<AnswerItemDTO> answerItemDTOS, Question question) {
        answerItemDTOS
                .forEach(answerItemDTO -> {
                    Answer answer = new Answer(answerItemDTO, question);
                    answerRepository.save(answer);
                });
    }
}
