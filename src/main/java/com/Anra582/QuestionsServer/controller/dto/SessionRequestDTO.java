package com.Anra582.QuestionsServer.controller.dto;

import java.util.List;

public class SessionRequestDTO {
    public String name;
    public List<AnsweredQuestionDTO> questionsList;

    public SessionRequestDTO() {
    }
}
