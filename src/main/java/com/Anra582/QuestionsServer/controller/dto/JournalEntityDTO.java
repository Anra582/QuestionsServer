package com.Anra582.QuestionsServer.controller.dto;

import com.Anra582.QuestionsServer.entity.Journal;

public class JournalEntityDTO {
    public String id;
    public String name;
    public Long defaultPageSize;

    public JournalEntityDTO(Journal journal) {
        this.id = journal.getId();
        this.name = journal.getName();
        this.defaultPageSize = journal.getDefaultPageSize();
    }
}
