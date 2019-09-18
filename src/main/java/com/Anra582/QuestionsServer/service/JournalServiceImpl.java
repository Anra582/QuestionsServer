package com.Anra582.QuestionsServer.service;

import com.Anra582.QuestionsServer.controller.dto.JournalItemDTO;
import com.Anra582.QuestionsServer.controller.dto.JournalRequestDTO;
import com.Anra582.QuestionsServer.controller.dto.QuestionItemDTO;
import com.Anra582.QuestionsServer.controller.dto.SessionItemDTO;
import com.Anra582.QuestionsServer.data.AnswerRepository;
import com.Anra582.QuestionsServer.data.JournalRepository;
import com.Anra582.QuestionsServer.data.QuestionRepository;
import com.Anra582.QuestionsServer.data.SessionRepository;
import com.Anra582.QuestionsServer.entity.Journal;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
        return journalRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    @Override
    public List<? extends JournalItemDTO> getJournalRows(String id, JournalRequestDTO journalRequestDTO) {
        List<? extends JournalItemDTO> collection;
        switch (id) {
            case QUESTIONS_JOURNAL_ID:
                collection = getCollection(
                        journalRequestDTO.search,
                        questionRepository::findByNameContainingIgnoreCase,
                        q -> new QuestionItemDTO(
                                q,
                                answerRepository.findByQuestion(q)));
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

        return collection;
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
}
