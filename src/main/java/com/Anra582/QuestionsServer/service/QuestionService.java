package com.Anra582.QuestionsServer.service;

import com.Anra582.QuestionsServer.controller.dto.QuestionItemDTO;

public interface QuestionService {
    QuestionItemDTO createQuestion(QuestionItemDTO questionItemDTO);

    QuestionItemDTO editQuestion(QuestionItemDTO questionItemDTO);
}
