package com.Anra582.QuestionsServer.controller;

import com.Anra582.QuestionsServer.controller.dto.*;
import com.Anra582.QuestionsServer.service.JournalService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/journal")
public class JournalRestController {

    private final JournalService journalService;

    public JournalRestController(JournalService journalService) {
        this.journalService = journalService;
    }

    @GetMapping("{id}")
    public JournalEntityDTO getJournalEntity(@PathVariable String id){
        return new JournalEntityDTO(journalService.getJournal(id));
    }

    @PutMapping("{id}/rows")
    public JournalResultDTO getRows(@PathVariable String id,
                                    @RequestBody JournalRequestDTO req) {
        JournalPageRowsDTO journalPageRowsDTO = journalService.getJournalRows(id, req);

        return new JournalResultDTO(journalPageRowsDTO.total, journalPageRowsDTO.journalItemDTOS);
    }
}
