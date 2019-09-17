package com.Anra582.QuestionsServer.service;

import com.Anra582.QuestionsServer.controller.dto.AnsweredQuestionDTO;
import com.Anra582.QuestionsServer.controller.dto.QuestionItemDTO;
import com.Anra582.QuestionsServer.controller.dto.SessionQuestionAnswerDTO;
import com.Anra582.QuestionsServer.controller.dto.SessionRequestDTO;
import com.Anra582.QuestionsServer.data.AnswerRepository;
import com.Anra582.QuestionsServer.data.QuestionRepository;
import com.Anra582.QuestionsServer.entity.Answer;
import com.Anra582.QuestionsServer.entity.Question;
import com.Anra582.QuestionsServer.entity.Session;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class SessionServiceImpl implements SessionService {

    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    public SessionServiceImpl(QuestionRepository questionRepository,
                              AnswerRepository answerRepository) {
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
    }


    @Override
    public List<QuestionItemDTO> getAllQuestions() {
        List<QuestionItemDTO> questions = new ArrayList<>();

        for (Question question : questionRepository.findAll()) {
            List<Answer> answers = answerRepository.findByQuestion(question);
            for(Answer answer : answers) {
                answer.setIsCorrect(false); //Gift for persons who knows how to watch requests/responses
            }
            questions.add(new QuestionItemDTO(question, answers));
        }

        return questions;
    }

    @Override
    public String saveNewSession(SessionRequestDTO sessionRequestDTO) {
        Session session = new Session();
        session.setFio(sessionRequestDTO.name);
        session.setLocalDateTime(LocalDateTime.now());

        double countSumOfAnswers = 0;
        double countSumOfRightAnswers = 0;


        for (AnsweredQuestionDTO answeredQuestionDTO : sessionRequestDTO.questionsList) {
            countSumOfAnswers = answeredQuestionDTO.answersList.size();
            System.out.println("Count of answers " + countSumOfAnswers);
            for(SessionQuestionAnswerDTO sessionQuestionAnswerDTO : answeredQuestionDTO.answersList)
            {
                Answer answer = answerRepository.findById(Long.valueOf(sessionQuestionAnswerDTO.id)).get();

                if(answer.getIsCorrect() == sessionQuestionAnswerDTO.isSelected) {
                    countSumOfRightAnswers++;
                }

            }
        }

        double totalPercent = countSumOfRightAnswers / countSumOfAnswers * 100;

        return String.valueOf(totalPercent);
    }
}
