package com.Anra582.QuestionsServer.controller;

import com.Anra582.QuestionsServer.controller.dto.AnswerItemDTO;
import com.Anra582.QuestionsServer.controller.dto.QuestionItemDTO;
import com.Anra582.QuestionsServer.controller.dto.SessionRequestDTO;
import com.Anra582.QuestionsServer.service.SessionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/session")
public class SessionRestController {

    private final SessionService sessionService;

    public SessionRestController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @PostMapping("")
    public String createSession(@RequestBody SessionRequestDTO sessionRequestDTO) {
        return sessionService.saveNewSession(sessionRequestDTO);
    }

    @GetMapping("questions-new")
    public List<QuestionItemDTO> getQuestions() {
        return sessionService.getAllQuestions();
    }
}
