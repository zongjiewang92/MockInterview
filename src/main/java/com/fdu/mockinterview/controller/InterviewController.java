package com.fdu.mockinterview.controller;

import com.fdu.mockinterview.entity.Interview;
import com.fdu.mockinterview.service.InterviewService;
import io.swagger.v3.oas.annotations.tags.Tag;
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
    public List<Interview> getAllInterviews() {
        return interviewService.getAllInterviews();
    }

    @GetMapping("/{id}")
    public Interview getInterviewById(@PathVariable Integer id) {
        return interviewService.getInterviewById(id);
    }

    @PostMapping("/createInterview")
    public Interview createInterview(@RequestBody Interview interview) {
        return interviewService.createInterview(interview);
    }

    @PutMapping("/updateInterview")
    public Interview updateInterview(@RequestBody Interview interview) {
        return interviewService.updateInterview(interview);
    }

    @DeleteMapping("/deleteInterview/{id}")
    public void deleteInterview(@PathVariable Integer id) {
        interviewService.deleteInterview(id);
    }
}
