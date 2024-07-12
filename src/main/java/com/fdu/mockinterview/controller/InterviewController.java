package com.fdu.mockinterview.controller;

import com.fdu.mockinterview.common.PageResult;
import com.fdu.mockinterview.common.Result;
import com.fdu.mockinterview.common.ResultBuilder;
import com.fdu.mockinterview.entity.Interview;
import com.fdu.mockinterview.entity.Question;
import com.fdu.mockinterview.service.InterviewService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


@RestController
@RequestMapping("/interview")
@Tag(name = "Interview Controller", description = "Interview management APIs")
public class InterviewController {

    @Resource
    private InterviewService interviewService;

    @GetMapping(value = "/getAllInterviews")
    public ResponseEntity<Result<List<Interview>>> getAllInterviews() {
        List<Interview> allInterviews = interviewService.getAllInterviews();
        return ResponseEntity.ok(ResultBuilder.success(allInterviews));
    }


    @GetMapping(value = "/getInterviewsByUserId/{userId}")
    public ResponseEntity<Result<List<Interview>>> getInterviewsByUserId(@PathVariable Integer userId) {
        return ResponseEntity.ok(ResultBuilder.success(interviewService.getAllInterviewsByUserId(userId)));
    }

    @GetMapping(value = "/selectByUserIdPages")
    public ResponseEntity<PageResult<List<Interview>>> selectByUserIdPages(@RequestParam(defaultValue = "1") int pageNum,
                                                                           @RequestParam(defaultValue = "10") int pageSize,
                                                                           @RequestParam int userId) {


        return interviewService.getAllInterviewsByUserIdPages(pageNum, pageSize, userId);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Result<Interview>> getInterviewById(@PathVariable Integer id) {
        return ResponseEntity.ok(ResultBuilder.success(interviewService.getInterviewById(id)));
    }

    @PostMapping("/createInterview")
    public ResponseEntity<Result<Interview>> createInterview(@RequestBody Interview interview) {
        return ResponseEntity.ok(ResultBuilder.success(interviewService.createInterview(
                interview.getUserId(), interview.getCvId(), interview.getCompanyName(), interview.getPosition())));
    }

    @PutMapping("/updateInterview")
    public ResponseEntity<Result<Interview>> updateInterview(@RequestBody Interview interview) {
        return ResponseEntity.ok(ResultBuilder.success(interviewService.updateInterview(interview)));
    }

    @DeleteMapping("/deleteInterview/{id}")
    public ResponseEntity<Result<Integer>> deleteInterview(@PathVariable Integer id) {
        interviewService.deleteInterview(id);
        return ResponseEntity.ok(ResultBuilder.success(id));
    }


    // Mock interview start api -> call AI -> get questions(update table question, return questions to front-end)
    @PostMapping("/startInterview")
    public ResponseEntity<List<Question>> startInterview(@RequestBody Interview interview) {
        return interviewService.startInterview(interview);
    }

    // Mock interview end api -> call AI -> get report, score -> update interview table -> return to front-end
    // Actually there is no endInterview, the interview result will be available after last question answered
    // so there only need to get the interview result
    @PostMapping("/getInterviewEvaluation")
    public ResponseEntity<Result<Interview>> getInterviewEvaluation(@RequestBody Interview interview) {

        return ResponseEntity.ok(ResultBuilder.success(interviewService.getInterviewEvaluation(interview)));

    }

}
