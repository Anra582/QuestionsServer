package com.Anra582.QuestionsServer.controller.dto;

import java.util.List;

public class JournalRequestDTO {
    public String search;
    public int page;
    public int pageSize;
    public List<FilterDTO> filters;

    public JournalRequestDTO() {
    }
}
