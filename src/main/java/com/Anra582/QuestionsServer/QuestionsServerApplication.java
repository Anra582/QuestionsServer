package com.Anra582.QuestionsServer;

import com.Anra582.QuestionsServer.data.JournalRepository;
import com.Anra582.QuestionsServer.entity.Journal;
import com.Anra582.QuestionsServer.service.JournalServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class QuestionsServerApplication {

	@Autowired
	private JournalRepository journalRepository;

	public static void main(String[] args) {
		SpringApplication.run(QuestionsServerApplication.class, args);
	}

	@PostConstruct
	public void init() {
		Journal questionsJournal = new Journal();
		questionsJournal.setId(JournalServiceImpl.QUESTIONS_JOURNAL_ID);
		questionsJournal.setName("Вопросы");
		questionsJournal.setDefaultPageSize(15L);
		journalRepository.save(questionsJournal);

		Journal sessionsJournal = new Journal();
		sessionsJournal.setId(JournalServiceImpl.SESSIONS_JOURNAL_ID);
		sessionsJournal.setName("Сессии");
		sessionsJournal.setDefaultPageSize(15L);
		journalRepository.save(sessionsJournal);
	}
}
