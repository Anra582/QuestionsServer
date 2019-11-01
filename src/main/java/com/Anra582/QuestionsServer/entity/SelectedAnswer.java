package com.Anra582.QuestionsServer.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class SelectedAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "answer_id")
    private Answer answer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "session_id")
    private Session session;

    public SelectedAnswer() {

    }

    public SelectedAnswer(Answer answer, Session session) {
        this.answer = answer;
        this.session = session;
    }
}
