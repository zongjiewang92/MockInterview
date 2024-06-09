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


    @GetMapping(value = "/getAllInterviewsByUserId/{userId}")
    public List<Interview> getAllResumesByUserId(@PathVariable Integer userId) {
        return interviewService.getAllInterviewsByUserId(userId);
    }

    @GetMapping(value = "/selectByUserIdPages")
    public List<Interview> selectByUserIdPages(@RequestParam(defaultValue = "1") int pageNum,
                                               @RequestParam(defaultValue = "10") int pageSize,
                                               @RequestParam int userId) {
        return interviewService.getAllInterviewsByUserIdPages(pageNum, pageSize, userId);
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


    // TODO Mock interview start api -> call AI -> get questions(update table question, return questions to front-end)


    // TODO Mock interview end api -> call AI -> get report, score -> update interview table -> return to front-end


}
