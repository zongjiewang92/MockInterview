package com.fdu.mockinterview.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fdu.mockinterview.common.PageResult;
import com.fdu.mockinterview.common.Result;
import com.fdu.mockinterview.common.ResultBuilder;
import com.fdu.mockinterview.entity.Question;
import com.fdu.mockinterview.service.QuestionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/question")
@Tag(name = "Question Controller", description = "Question management APIs")
public class QuestionController {

    @Resource
    private QuestionService questionService;
    private final WebClient webClient;

    public QuestionController() {
        this.webClient = WebClient.create("http://localhost:5000");
    }

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
    @PostMapping("/answerQuestion")
    public ResponseEntity<String> answerQuestion(@RequestBody Integer questionId,
                                                 @RequestPart(name = "answerAudio") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The answer audio is empty.");
        }

        // upload answer media file
        String filePathName = questionService.uploadAnswerFile(file, questionId);
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode jsonObject = objectMapper.createObjectNode();
        jsonObject.put("user_input", filePathName);

        // call AI service and return result
        return webClient.post()
                .uri("/service")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(jsonObject)
                .retrieve()
                .toEntity(String.class)
                .block();
    }

    // TODO review -> download answer media file, provide to front-end
    @GetMapping("/download/{questionId:.+}")
    @Operation(summary = "Download an answer audio file")
    public ResponseEntity<org.springframework.core.io.Resource> downloadAnswer(@PathVariable Integer questionId) {

        return questionService.downloadAnswerFile(questionId);
    }

}
