package com.Anra582.QuestionsServer.service;

import com.Anra582.QuestionsServer.controller.dto.AnswerItemDTO;
import com.Anra582.QuestionsServer.controller.dto.QuestionItemDTO;
import com.Anra582.QuestionsServer.data.AnswerRepository;
import com.Anra582.QuestionsServer.data.QuestionRepository;
import com.Anra582.QuestionsServer.entity.Answer;
import com.Anra582.QuestionsServer.entity.Question;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

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

        for (AnswerItemDTO answerDTO : questionItemDTO.answers) {
            Answer answer = new Answer();
            answer.setName(answerDTO.answerText);
            answer.setIsCorrect(answerDTO.isCorrect);
            answer.setQuestion(question);

            answerRepository.save(answer);
        }

        return new QuestionItemDTO(question,
                answerRepository.findByQuestion(question));
    }

    @Override
    public QuestionItemDTO editQuestion(QuestionItemDTO questionItemDTO) {

        Question question1 = new Question();
        question1.setId(Long.valueOf(questionItemDTO.id));

        for (Answer answer: answerRepository.findByQuestion(question1))
        {
            answerRepository.deleteById(answer.getId());
        }

        questionRepository.deleteById(Long.valueOf(questionItemDTO.id));

        return createQuestion(questionItemDTO);
    }
}
