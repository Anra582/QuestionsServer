package com.Anra582.QuestionsServer.controller.dto;

import com.Anra582.QuestionsServer.entity.Answer;

public class AnswerItemDTO {
    public String id;
    public String answerText;
    public Boolean isCorrect;

    public AnswerItemDTO() {

    }

    public AnswerItemDTO(Answer answer) {
        id = answer.getId().toString();
        answerText = answer.getName();
        isCorrect = answer.getIsCorrect();
    }
}
