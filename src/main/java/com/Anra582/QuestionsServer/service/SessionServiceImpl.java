package com.Anra582.QuestionsServer.service;

import com.Anra582.QuestionsServer.controller.dto.AnsweredQuestionDTO;
import com.Anra582.QuestionsServer.controller.dto.QuestionItemDTO;
import com.Anra582.QuestionsServer.controller.dto.SessionQuestionAnswerDTO;
import com.Anra582.QuestionsServer.controller.dto.SessionRequestDTO;
import com.Anra582.QuestionsServer.data.AnswerRepository;
import com.Anra582.QuestionsServer.data.QuestionRepository;
import com.Anra582.QuestionsServer.data.SelectedAnswerRepository;
import com.Anra582.QuestionsServer.data.SessionRepository;
import com.Anra582.QuestionsServer.entity.Answer;
import com.Anra582.QuestionsServer.entity.Question;
import com.Anra582.QuestionsServer.entity.SelectedAnswer;
import com.Anra582.QuestionsServer.entity.Session;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class SessionServiceImpl implements SessionService {

    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final SessionRepository sessionRepository;
    private final SelectedAnswerRepository selectedAnswerRepository;

    public SessionServiceImpl(QuestionRepository questionRepository,
                              AnswerRepository answerRepository, SessionRepository sessionRepository, SelectedAnswerRepository selectedAnswerRepository) {
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
        this.sessionRepository = sessionRepository;
        this.selectedAnswerRepository = selectedAnswerRepository;
    }


    @Override
    public List<QuestionItemDTO> getAllQuestions() {
        List<QuestionItemDTO> questions = new ArrayList<>();

        for (Question question : questionRepository.findAll()) {
            List<Answer> answers = answerRepository.findByQuestion(question);
            List<Answer> clearedAnswers = clearAnswers(answers);

            questions.add(new QuestionItemDTO(question, clearedAnswers));
        }

        return questions;
    }

    @Override
    public String saveNewSession(SessionRequestDTO sessionRequestDTO) {
        Session session = new Session();
        session.setFio(sessionRequestDTO.name);
        session.setLocalDateTime(LocalDateTime.now());

        sessionRepository.save(session);

        double countSumOfAnswers = 0;
        double countSumOfRightAnswers = 0;

        for (AnsweredQuestionDTO answeredQuestionDTO : sessionRequestDTO.questionsList) {
            for (SessionQuestionAnswerDTO sessionQuestionAnswerDTO : answeredQuestionDTO.answersList) {
                Answer answer = getAnswerById(Long.valueOf(sessionQuestionAnswerDTO.id));

                if (sessionQuestionAnswerDTO.isSelected) {
                    selectedAnswerRepository.save(new SelectedAnswer(answer, session));

                    countSumOfAnswers++;
                    if (answer.getIsCorrect()) {
                        countSumOfRightAnswers++;
                    }
                }
            }
        }

        double totalPercent = countSumOfRightAnswers / countSumOfAnswers * 100;
        double formattedTotalPercent = Math.round(totalPercent * 100.0) / 100.0;

        session.setPercent(formattedTotalPercent);

        return String.valueOf(formattedTotalPercent);
    }

    private Answer getAnswerById(Long id) {
        return answerRepository.findById(id).orElseThrow(() -> new RuntimeException("Cannot find Answer by id"));
    }

    private List<Answer> clearAnswers(List<Answer> answers) {
        return answers.stream().map(this::clearAnswer).collect(Collectors.toList());
    }

    private Answer clearAnswer(Answer answer) {
        Answer clearedAnswer = new Answer();
        clearedAnswer.setId(answer.getId());
        clearedAnswer.setName(answer.getName());
        clearedAnswer.setQuestion(answer.getQuestion());
        return clearedAnswer;
    }
}
