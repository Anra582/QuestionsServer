package com.Anra582.QuestionsServer.service;

import com.Anra582.QuestionsServer.controller.dto.JournalItemDTO;
import com.Anra582.QuestionsServer.controller.dto.JournalRequestDTO;
import com.Anra582.QuestionsServer.entity.Journal;

import java.util.List;

public interface JournalService {

    Journal getJournal(String id);

    List<? extends JournalItemDTO> getJournalRows(String id, JournalRequestDTO journalRequestDTO);
}
