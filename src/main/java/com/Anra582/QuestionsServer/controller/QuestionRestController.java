package com.Anra582.QuestionsServer.controller;

import com.Anra582.QuestionsServer.controller.dto.QuestionItemDTO;
import com.Anra582.QuestionsServer.service.QuestionService;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/question")
public class QuestionRestController {

    private final QuestionService questionService;

    public QuestionRestController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @PostMapping("create")
    public QuestionItemDTO create(@RequestBody QuestionItemDTO questionItemDTO) {
        return questionService.createQuestion(questionItemDTO);
    }

    @PutMapping("edit")
    public QuestionItemDTO edit(@RequestBody QuestionItemDTO questionItemDTO) {
        return questionService.editQuestion(questionItemDTO);
    }
}
