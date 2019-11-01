package com.Anra582.QuestionsServer.controller.dto;

import com.Anra582.QuestionsServer.entity.Session;

import java.time.LocalDateTime;

public class SessionItemDTO extends JournalItemDTO {
    public String id;
    public String name;
    public LocalDateTime insertDate;
    public Double result;

    public SessionItemDTO() {

    }

    public SessionItemDTO(Session session) {
        this.id = session.getId().toString();
        this.name = session.getFio();
        this.insertDate = session.getLocalDateTime();
        this.result = session.getPercent();
    }

}
