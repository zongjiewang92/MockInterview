package com.fdu.mockinterview.controller;

import com.fdu.mockinterview.common.PageResult;
import com.fdu.mockinterview.common.Result;
import com.fdu.mockinterview.common.ResultBuilder;
import com.fdu.mockinterview.entity.Question;
import com.fdu.mockinterview.service.QuestionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Result<List<Question>>> getAllQuestions() {
        return ResponseEntity.ok(ResultBuilder.success(questionService.getAllQuestions()));
    }


    @GetMapping(value = "/selectByInterviewIdPages")
    public ResponseEntity<PageResult<List<Question>>> selectByInterviewIdPages(@RequestParam(defaultValue = "1") int pageNum,
                                                                               @RequestParam(defaultValue = "10") int pageSize,
                                                                               @RequestParam int interviewId) {
        List<Question> questionList = questionService.getQuestionByUserIdPages(pageNum, pageSize, interviewId);
        long totalElements = questionService.countQuestionByUserId(interviewId);
        int totalPages = (int) Math.ceil((double) totalElements / pageSize);

        return ResponseEntity.ok(ResultBuilder.paginatedSuccess(questionList, pageNum, pageSize, totalElements, totalPages));
    }


    @GetMapping("/{id}")
    public ResponseEntity<Result<Question>> getQuestionById(@PathVariable Integer id) {
        return ResponseEntity.ok(ResultBuilder.success(questionService.getQuestionById(id)));
    }

    @PostMapping("/createQuestion")
    public ResponseEntity<Result<Question>> createQuestion(@RequestBody Question question) {
        return ResponseEntity.ok(ResultBuilder.success(questionService.createQuestion(question)));
    }

    @PutMapping("/updateQuestion")
    public ResponseEntity<Result<Question>> updateQuestion(@RequestBody Question question) {
        return ResponseEntity.ok(ResultBuilder.success(questionService.updateQuestion(question)));
    }

    @DeleteMapping("/deleteQuestion/{id}")
    public ResponseEntity<Result<Integer>> deleteQuestion(@PathVariable Integer id) {
        questionService.deleteQuestion(id);
        return ResponseEntity.ok(ResultBuilder.success(id));
    }


    // TODO question answer end -> upload answer media file -> save media file -> call AI do sound transit to text, get AI_score, AI_result -> save answer text to table

    // TODO review -> download answer media file, provide to front-end


}
