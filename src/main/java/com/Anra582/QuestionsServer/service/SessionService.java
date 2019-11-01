package com.Anra582.QuestionsServer.service;

import com.Anra582.QuestionsServer.controller.dto.QuestionItemDTO;
import com.Anra582.QuestionsServer.controller.dto.SessionRequestDTO;

import java.util.List;

public interface SessionService {
    List<QuestionItemDTO> getAllQuestions();
    String saveNewSession(SessionRequestDTO sessionRequestDTO);
}
