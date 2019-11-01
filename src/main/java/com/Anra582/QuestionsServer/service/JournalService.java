package com.Anra582.QuestionsServer.service;

import com.Anra582.QuestionsServer.controller.dto.JournalPageRowsDTO;
import com.Anra582.QuestionsServer.controller.dto.JournalRequestDTO;
import com.Anra582.QuestionsServer.entity.Journal;

public interface JournalService {

    Journal getJournal(String id);

    JournalPageRowsDTO getJournalRows(String id, JournalRequestDTO journalRequestDTO);
}
