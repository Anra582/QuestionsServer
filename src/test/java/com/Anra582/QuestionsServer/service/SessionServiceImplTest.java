package com.Anra582.QuestionsServer.service;

import com.Anra582.QuestionsServer.QuestionsServerApplication;
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
import com.Anra582.QuestionsServer.entity.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = QuestionsServerApplication.class)
public class SessionServiceImplTest {

    @Autowired
    private SessionService sessionService;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private SelectedAnswerRepository selectedAnswerRepository;

    private Question firstQuestion;
    private Question secondQuestion;
    private Answer firstAnswer;
    private Answer oneAndHalfAnswer;
    private Answer secondAnswer;

    @Before
    public void init() {
        firstQuestion = new Question();
        firstQuestion.setName("first question");
        questionRepository.save(firstQuestion);

        firstAnswer = new Answer();
        firstAnswer.setName("answer for first question");
        firstAnswer.setQuestion(firstQuestion);
        firstAnswer.setIsCorrect(true);
        answerRepository.save(firstAnswer);

        oneAndHalfAnswer = new Answer();
        oneAndHalfAnswer.setName("another answer for first question");
        oneAndHalfAnswer.setQuestion(firstQuestion);
        oneAndHalfAnswer.setIsCorrect(false);
        answerRepository.save(oneAndHalfAnswer);

        secondQuestion = new Question();
        secondQuestion.setName("second question");
        questionRepository.save(secondQuestion);

        secondAnswer = new Answer();
        secondAnswer.setName("answer for second question");
        secondAnswer.setQuestion(secondQuestion);
        secondAnswer.setIsCorrect(true);
        answerRepository.save(secondAnswer);
    }

    @After
    public void clearRepos() {
        selectedAnswerRepository.deleteAll();
        answerRepository.deleteAll();
        questionRepository.deleteAll();
        sessionRepository.deleteAll();
    }

    @Test
    public void getAllQuestionsTest() {

        List<QuestionItemDTO> questionItemDTOS = sessionService.getAllQuestions();
        assertEquals(2, questionItemDTOS.size());
        assertSame(questionItemDTOS.get(0).name, firstQuestion.getName());
        assertSame(questionItemDTOS.get(1).name, secondQuestion.getName());
    }

    @Test
    public void saveNewSessionTest() {
        SessionQuestionAnswerDTO firstQFirstA = new SessionQuestionAnswerDTO();
        firstQFirstA.id = String.valueOf(firstAnswer.getId());
        firstQFirstA.isSelected = true;

        SessionQuestionAnswerDTO firstQSecondA = new SessionQuestionAnswerDTO();
        firstQSecondA.id = String.valueOf(oneAndHalfAnswer.getId());
        firstQSecondA.isSelected = true;

        SessionQuestionAnswerDTO secondQFirstA = new SessionQuestionAnswerDTO();
        secondQFirstA.id = String.valueOf(secondAnswer.getId());
        secondQFirstA.isSelected = true;


        AnsweredQuestionDTO answeredFirstQuestionDTO = new AnsweredQuestionDTO();
        answeredFirstQuestionDTO.id = "1";
        answeredFirstQuestionDTO.answersList = Arrays.asList(firstQFirstA, firstQSecondA);

        AnsweredQuestionDTO answeredSecondQuestionDTO = new AnsweredQuestionDTO();
        answeredSecondQuestionDTO.id = "4";
        answeredSecondQuestionDTO.answersList = Arrays.asList(secondQFirstA);


        SessionRequestDTO sessionRequestDTO = new SessionRequestDTO();
        sessionRequestDTO.name = "Tester";
        sessionRequestDTO.questionsList = Arrays.asList(answeredFirstQuestionDTO, answeredSecondQuestionDTO);

        sessionService.saveNewSession(sessionRequestDTO);

        Session returnedSession = sessionRepository.findByFioContainingIgnoreCase("Tester")
                .stream().findFirst().get();

        assertEquals(returnedSession.getFio(), sessionRequestDTO.name);
        assertEquals(returnedSession.getPercent(), Double.valueOf("66.67"));
    }
}