package com.fdu.mockinterview.controller;

import com.fdu.mockinterview.entity.Question;
import com.fdu.mockinterview.entity.Resume;
import com.fdu.mockinterview.service.QuestionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


@RestController
@RequestMapping("/question")
@Tag(name = "Question Controller", description = "Question management APIs")
public class QuestionController {

    @Resource
    private QuestionService questionService;

    @GetMapping(value = "/getAllQuestions")
    public List<Question> getAllQuestions() {
        return questionService.getAllQuestions();
    }


    @GetMapping(value = "/selectByInterviewIdPages")
    public List<Question> selectByInterviewIdPages(@RequestParam(defaultValue = "1") int pageNum,
                                               @RequestParam(defaultValue = "10") int pageSize,
                                               @RequestParam int interviewId) {
        return questionService.getAllQuestionByUserIdPages(pageNum, pageSize, interviewId);
    }


    @GetMapping("/{id}")
    public Question getQuestionById(@PathVariable Integer id) {
        return questionService.getQuestionById(id);
    }

    @PostMapping("/createQuestion")
    public Question createQuestion(@RequestBody Question question) {
        return questionService.createQuestion(question);
    }

    @PutMapping("/updateQuestion")
    public Question updateQuestion(@RequestBody Question question) {
        return questionService.updateQuestion(question);
    }

    @DeleteMapping("/deleteQuestion/{id}")
    public void deleteQuestion(@PathVariable Integer id) {
        questionService.deleteQuestion(id);
    }
}
