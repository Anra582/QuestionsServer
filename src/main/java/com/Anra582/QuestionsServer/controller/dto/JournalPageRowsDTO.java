package com.Anra582.QuestionsServer.controller.dto;

import java.util.List;

public class JournalPageRowsDTO {
    public int total;
    public List<? extends JournalItemDTO> journalItemDTOS;

    public JournalPageRowsDTO() {

    }

    public JournalPageRowsDTO(int size, List<? extends JournalItemDTO> page) {
        this.total = size;
        this.journalItemDTOS = page;
    }
}
