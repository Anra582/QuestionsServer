package com.Anra582.QuestionsServer.service;

import com.Anra582.QuestionsServer.QuestionsServerApplication;
import com.Anra582.QuestionsServer.controller.dto.FilterDTO;
import com.Anra582.QuestionsServer.controller.dto.JournalPageRowsDTO;
import com.Anra582.QuestionsServer.controller.dto.JournalRequestDTO;
import com.Anra582.QuestionsServer.controller.dto.QuestionItemDTO;
import com.Anra582.QuestionsServer.data.AnswerRepository;
import com.Anra582.QuestionsServer.data.JournalRepository;
import com.Anra582.QuestionsServer.data.QuestionRepository;
import com.Anra582.QuestionsServer.data.SessionRepository;
import com.Anra582.QuestionsServer.entity.Answer;
import com.Anra582.QuestionsServer.entity.Journal;
import com.Anra582.QuestionsServer.entity.Question;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = QuestionsServerApplication.class)
public class JournalServiceImplTest {

    @Autowired
    private JournalService journalService;

    @Autowired
    private JournalRepository journalRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    private Journal questionsJournal;
    private Question firstQuestion;
    private Question secondQuestion;

    @Before
    public void init() {
        questionsJournal = new Journal();
        questionsJournal.setId(JournalServiceImpl.QUESTIONS_JOURNAL_ID);
        questionsJournal.setName("Вопросы");
        questionsJournal.setDefaultPageSize(15L);
        journalRepository.save(questionsJournal);

        firstQuestion = new Question();
        firstQuestion.setName("first question");
        questionRepository.save(firstQuestion);

        Answer firstAnswer = new Answer();
        firstAnswer.setName("answer for first question");
        firstAnswer.setQuestion(firstQuestion);
        firstAnswer.setIsCorrect(true);
        answerRepository.save(firstAnswer);

        Answer oneAndHalfAnswer = new Answer();
        oneAndHalfAnswer.setName("another answer for first question");
        oneAndHalfAnswer.setQuestion(firstQuestion);
        oneAndHalfAnswer.setIsCorrect(false);
        answerRepository.save(oneAndHalfAnswer);

        secondQuestion = new Question();
        secondQuestion.setName("second question");
        questionRepository.save(secondQuestion);

        Answer secondAnswer = new Answer();
        secondAnswer.setName("answer for second question");
        secondAnswer.setQuestion(secondQuestion);
        secondAnswer.setIsCorrect(true);
        answerRepository.save(secondAnswer);
    }

    @After
    public void clearRepos() {
        answerRepository.deleteAll();
        questionRepository.deleteAll();
        journalRepository.deleteAll();
    }

    @Test
    public void getJournalTest() {

        Journal foundedJournal = journalService.getJournal(JournalServiceImpl.QUESTIONS_JOURNAL_ID);

        assertEquals(questionsJournal.getId(), foundedJournal.getId());
        assertEquals(questionsJournal.getName(), foundedJournal.getName());
        assertEquals(questionsJournal.getDefaultPageSize(), foundedJournal.getDefaultPageSize());
    }

    @Test
    public void getJournalRowsTotalCountTest() {

        FilterDTO filterDTO = new FilterDTO();
        filterDTO.code = "";
        filterDTO.type = "";

        List<FilterDTO> filterDTOS = Collections.singletonList(filterDTO);

        JournalRequestDTO journalRequestDTO = new JournalRequestDTO();
        journalRequestDTO.search = "";
        journalRequestDTO.page = 1;
        journalRequestDTO.pageSize = 15;
        journalRequestDTO.filters = filterDTOS;

        JournalPageRowsDTO jprDTO = journalService.getJournalRows(JournalServiceImpl.QUESTIONS_JOURNAL_ID, journalRequestDTO);

        assertEquals(2, jprDTO.total);
    }

    @Test
    public void getJournalRowsPagingTest() {

        FilterDTO filterDTO = new FilterDTO();
        filterDTO.code = "";
        filterDTO.type = "";

        List<FilterDTO> filterDTOS = Collections.singletonList(filterDTO);

        JournalRequestDTO journalRequestDTO = new JournalRequestDTO();
        journalRequestDTO.search = "";
        journalRequestDTO.page = 2;
        journalRequestDTO.pageSize = 1;
        journalRequestDTO.filters = filterDTOS;

        JournalPageRowsDTO jprDTO = journalService.getJournalRows(JournalServiceImpl.QUESTIONS_JOURNAL_ID, journalRequestDTO);

        QuestionItemDTO questionItemDTO = (QuestionItemDTO) jprDTO.journalItemDTOS.stream().findFirst().get();
        assertSame("second question", questionItemDTO.name);
    }

    @Test
    public void getJournalRowsFilteredByCountOfAnswersTest() {

        FilterDTO filterDTO = new FilterDTO();
        filterDTO.code = "";
        filterDTO.type = "";
        filterDTO.value = 2L;

        List<FilterDTO> filterDTOS = Collections.singletonList(filterDTO);

        JournalRequestDTO journalRequestDTO = new JournalRequestDTO();
        journalRequestDTO.search = "";
        journalRequestDTO.page = 1;
        journalRequestDTO.pageSize = 15;
        journalRequestDTO.filters = filterDTOS;

        JournalPageRowsDTO jprDTO = journalService.getJournalRows(JournalServiceImpl.QUESTIONS_JOURNAL_ID, journalRequestDTO);

        QuestionItemDTO questionItemDTO = (QuestionItemDTO) jprDTO.journalItemDTOS.stream().findFirst().get();
        assertSame(questionItemDTO.name, firstQuestion.getName());
    }

    @Test
    public void getJournalRowsFilteredBySearchTest() {

        FilterDTO filterDTO = new FilterDTO();
        filterDTO.code = "";
        filterDTO.type = "";

        List<FilterDTO> filterDTOS = Collections.singletonList(filterDTO);

        JournalRequestDTO journalRequestDTO = new JournalRequestDTO();
        journalRequestDTO.search = "second";
        journalRequestDTO.page = 1;
        journalRequestDTO.pageSize = 15;
        journalRequestDTO.filters = filterDTOS;

        JournalPageRowsDTO jprDTO = journalService.getJournalRows(JournalServiceImpl.QUESTIONS_JOURNAL_ID, journalRequestDTO);

        QuestionItemDTO questionItemDTO = (QuestionItemDTO) jprDTO.journalItemDTOS.stream().findFirst().get();
        assertSame(questionItemDTO.name, secondQuestion.getName());
    }
}