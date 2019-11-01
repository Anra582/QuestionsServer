package com.Anra582.QuestionsServer.entity;

import com.Anra582.QuestionsServer.controller.dto.AnswerItemDTO;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Question question;

    @Column
    private Boolean isCorrect;

    public Answer() {

    }

    public Answer(AnswerItemDTO answerItemDTO, Question question) {
        this.setName(answerItemDTO.answerText);
        this.setIsCorrect(answerItemDTO.isCorrect);
        this.setQuestion(question);
    }

}
