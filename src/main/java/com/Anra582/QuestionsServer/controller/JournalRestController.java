package com.Anra582.QuestionsServer.controller;

import com.Anra582.QuestionsServer.controller.dto.JournalEntityDTO;
import com.Anra582.QuestionsServer.controller.dto.JournalItemDTO;
import com.Anra582.QuestionsServer.controller.dto.JournalRequestDTO;
import com.Anra582.QuestionsServer.controller.dto.JournalResultDTO;
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
        List<? extends JournalItemDTO> collect =
                journalService.getJournalRows(id, req);

        return new JournalResultDTO(collect.size(), collect);
    }
}
