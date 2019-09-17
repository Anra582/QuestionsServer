package com.Anra582.QuestionsServer.controller.dto;

import com.Anra582.QuestionsServer.entity.Answer;
import com.Anra582.QuestionsServer.entity.Question;

import java.util.List;
import java.util.stream.Collectors;

public class QuestionItemDTO extends JournalItemDTO {
    public String name;
    public List<AnswerItemDTO> answers;

    public QuestionItemDTO() {

    }

    public QuestionItemDTO(Question question, List<Answer> answers) {
        this.id = question.getId().toString();
        this.name = question.getName();
        this.answers = answers.stream()
                .map(AnswerItemDTO::new)
                .collect(Collectors.toList());
    }
}
