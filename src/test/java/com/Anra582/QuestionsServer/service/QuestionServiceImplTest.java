package com.Anra582.QuestionsServer.service;

import com.Anra582.QuestionsServer.QuestionsServerApplication;
import com.Anra582.QuestionsServer.controller.dto.AnswerItemDTO;
import com.Anra582.QuestionsServer.controller.dto.QuestionItemDTO;
import com.Anra582.QuestionsServer.data.AnswerRepository;
import com.Anra582.QuestionsServer.data.QuestionRepository;
import com.Anra582.QuestionsServer.entity.Answer;
import com.Anra582.QuestionsServer.entity.Question;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertSame;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = QuestionsServerApplication.class)
public class QuestionServiceImplTest {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    private AnswerItemDTO aidFirst;
    private AnswerItemDTO aidSecond;
    private QuestionItemDTO questionItemDTO;


    @Before
    public void init() {
        aidFirst = new AnswerItemDTO();
        aidFirst.answerText = "I don't know";
        aidFirst.isCorrect = true;

        aidSecond = new AnswerItemDTO();
        aidSecond.answerText = "What?";
        aidSecond.isCorrect = false;

        List<AnswerItemDTO> answerItemDTOS = Arrays.asList(aidFirst, aidSecond);

        questionItemDTO = new QuestionItemDTO();
        questionItemDTO.name = "Where is detonator?";
        questionItemDTO.answers = answerItemDTOS;
    }

    @After
    public void clearRepos() {
        answerRepository.deleteAll();
        questionRepository.deleteAll();
    }

    @Test
    public void createQuestionTest() {

        QuestionItemDTO answeredQuestionItemDTO = questionService.createQuestion(questionItemDTO);
        assertSame(answeredQuestionItemDTO.name, questionItemDTO.name);
        assertSame(answeredQuestionItemDTO.answers.get(0).answerText, aidFirst.answerText);
        assertSame(answeredQuestionItemDTO.answers.get(0).isCorrect, aidFirst.isCorrect);
        assertSame(answeredQuestionItemDTO.answers.get(1).answerText, aidSecond.answerText);
        assertSame(answeredQuestionItemDTO.answers.get(1).isCorrect, aidSecond.isCorrect);
    }

    @Test
    public void editQuestionTest() {

        Question question = new Question();
        question.setName("Some question");
        questionRepository.save(question);

        Answer firstAnswer = new Answer();
        firstAnswer.setName("answer for question");
        firstAnswer.setQuestion(question);
        firstAnswer.setIsCorrect(true);
        answerRepository.save(firstAnswer);

        Question foundedQuestion = questionRepository
                .findByNameContainingIgnoreCase("Some question").stream().findFirst().get();

        questionItemDTO.id = String.valueOf(foundedQuestion.getId());


        QuestionItemDTO answeredQuestionItemDTO = questionService.editQuestion(questionItemDTO);
        Question editedQuestion = questionRepository
                .findByNameContainingIgnoreCase(answeredQuestionItemDTO.name).stream().findFirst().get();
        assertSame(foundedQuestion.getId(), editedQuestion.getId());
        assertSame(editedQuestion.getName(), questionItemDTO.name);
        assertSame(answeredQuestionItemDTO.answers.get(0).answerText, aidFirst.answerText);
        assertSame(answeredQuestionItemDTO.answers.get(0).isCorrect, aidFirst.isCorrect);
        assertSame(answeredQuestionItemDTO.answers.get(1).answerText, aidSecond.answerText);
        assertSame(answeredQuestionItemDTO.answers.get(1).isCorrect, aidSecond.isCorrect);
    }
}