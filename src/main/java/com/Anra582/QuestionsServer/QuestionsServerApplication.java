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
		Journal journal = new Journal();
		journal.setId(JournalServiceImpl.QUESTIONS_JOURNAL_ID);
		journal.setName("Вопросы");
		journal.setDefaultPageSize(15L);
		journalRepository.save(journal);
	}
}
