package com.Anra582.QuestionsServer.service;

import com.Anra582.QuestionsServer.controller.dto.*;
import com.Anra582.QuestionsServer.data.AnswerRepository;
import com.Anra582.QuestionsServer.data.JournalRepository;
import com.Anra582.QuestionsServer.data.QuestionRepository;
import com.Anra582.QuestionsServer.data.SessionRepository;
import com.Anra582.QuestionsServer.entity.Journal;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Transactional
public class JournalServiceImpl implements JournalService {

    public static final String QUESTIONS_JOURNAL_ID = "questions";
    public static final String SESSIONS_JOURNAL_ID = "sessions";

    private final JournalRepository journalRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final SessionRepository sessionRepository;

    public JournalServiceImpl(JournalRepository journalRepository,
                              QuestionRepository questionRepository,
                              AnswerRepository answerRepository,
                              SessionRepository sessionRepository) {
        this.journalRepository = journalRepository;
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
        this.sessionRepository = sessionRepository;
    }

    @Override
    public Journal getJournal(String id) {
        return journalRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException(String.format("Cannot find Journal by id = %s", id)));
    }

    @Override
    public JournalPageRowsDTO getJournalRows(String id, JournalRequestDTO journalRequestDTO) {
        List<? extends JournalItemDTO> collection;
        switch (id) {
            case QUESTIONS_JOURNAL_ID:
                collection = getCollection(
                        journalRequestDTO.search,
                        questionRepository::findByNameContainingIgnoreCase,
                        q -> new QuestionItemDTO(
                                q,
                                answerRepository.findByQuestion(q)));

                if (isFilterValueExist(journalRequestDTO)) {
                    collection = filterByCountOfAnswers(collection, journalRequestDTO);
                }
                break;

            case SESSIONS_JOURNAL_ID:
                collection = getCollection(
                        journalRequestDTO.search,
                        sessionRepository:: findByFioContainingIgnoreCase,
                        SessionItemDTO::new);
                break;
            default:
                throw new RuntimeException();
        }
        return new JournalPageRowsDTO(collection.size(), getPage(collection, journalRequestDTO.page, journalRequestDTO.pageSize));
    }

    private <T, U extends JournalItemDTO> List<U> getCollection(
            String search,
            Function<String, List<T>> finder,
            Function<T, U> mapper
    ) {
        return finder.apply(search)
                .stream()
                .map(mapper)
                .collect(Collectors.toList());
    }

    private List<? extends JournalItemDTO> getPage(List<? extends JournalItemDTO> sourceList, int page, int pageSize) {
        if (pageSize <= 0 || page <= 0) {
            throw new IllegalArgumentException("invalid page size: " + pageSize);
        }

        int fromIndex = (page - 1) * pageSize;
        if (sourceList == null || sourceList.size() < fromIndex) {
            return Collections.emptyList();
        }

        return sourceList.subList(fromIndex, Math.min(fromIndex + pageSize, sourceList.size()));
    }

    private List<? extends JournalItemDTO> filterByCountOfAnswers(List<? extends JournalItemDTO> journalItemDTOS,
                                                         JournalRequestDTO journalRequestDTO) {

        return journalItemDTOS
                .stream()
                .filter(questionItemDTO -> answerRepository.countAnswersByQuestion
                        (questionRepository.findById(Long.valueOf(questionItemDTO.id)).get())
                        .equals(journalRequestDTO.filters.stream().findFirst().get().value))
                .collect(Collectors.toList());
    }

    private Boolean isFilterValueExist(JournalRequestDTO journalRequestDTO) {
        if (journalRequestDTO.filters.stream().findFirst().isPresent()) {
            Long testContaining = journalRequestDTO.filters.stream().findFirst().get().value;

            return testContaining != null;
        }
        return false;
    }
}
