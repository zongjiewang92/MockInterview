package com.fdu.mockinterview.service.Imp;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fdu.mockinterview.common.PageResult;
import com.fdu.mockinterview.common.ResultBuilder;
import com.fdu.mockinterview.entity.Interview;
import com.fdu.mockinterview.entity.Question;
import com.fdu.mockinterview.entity.Resume;
import com.fdu.mockinterview.mapper.InterviewMapper;
import com.fdu.mockinterview.service.InterviewService;
import com.fdu.mockinterview.service.QuestionService;
import com.fdu.mockinterview.service.ResumeService;
import com.fdu.mockinterview.service.WebClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


@Service("interviewService")
public class InterviewServiceImpl implements InterviewService {
    private static final Logger logger = LoggerFactory.getLogger(InterviewService.class);

    @Resource
    private InterviewMapper interviewMapper;
    @Resource
    private ResumeService resumeService;
    @Resource
    private QuestionService questionService;

    @Resource
    private WebClientService webClientService;


    @Override
    public List<Interview> getAllInterviews() {
        return interviewMapper.selectAll();
    }

    @Override
    public List<Interview> getAllInterviewsByUserId(Integer userId) {
        return interviewMapper.selectInterviewByUserId(userId);
    }

    @Override
    public ResponseEntity<PageResult<List<Interview>>> getAllInterviewsByUserIdPages(int pageNum, int pageSize, Integer userId) {
        int offset = (pageNum - 1) * pageSize;
        List<Interview> interviewList = interviewMapper.selectInterviewByUserIdPages(offset, pageSize, userId);

        long totalElements = interviewMapper.countInterviewByUserId(userId);
        int totalPages = (int) Math.ceil((double) totalElements / pageSize);

        return ResponseEntity.ok(ResultBuilder.paginatedSuccess(interviewList, pageNum, pageSize, totalElements, totalPages));
    }

    @Override
    public Integer countInterviewByUserId(Integer userId) {
        return interviewMapper.countInterviewByUserId(userId);
    }

    @Override
    public Interview getInterviewById(Integer id) {
        return interviewMapper.selectByPrimaryKey(id);
    }

    @Override
    public Interview createInterview(Integer userId, Integer cvId, String companyName, String position) {
        Interview interview = new Interview();
        interview.setUserId(userId);
        interview.setCvId(cvId);
        interview.setCompanyName(companyName);
        interview.setPosition(position);
        return interviewMapper.selectByPrimaryKey(interview.getId());
    }

    @Override
    public Interview updateInterview(Interview interview) {
        interviewMapper.updateByPrimaryKey(interview);
        return interviewMapper.selectByPrimaryKey(interview.getId());
    }

    @Override
    public void deleteInterview(Integer id) {
        interviewMapper.deleteByPrimaryKey(id);
    }

    @Override
    public ResponseEntity<List<Question>> startInterview(Interview interview) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode jsonObject = objectMapper.createObjectNode();
        jsonObject.put("company", interview.getCompanyName());
        jsonObject.put("position", interview.getPosition());
        Resume resume = resumeService.getResumeById(interview.getCvId());
        String cvContext = resume.getCvContext();
        try {
            JsonNode jsonNode = objectMapper.readTree(cvContext);
            jsonObject.put("resume_text", jsonNode.get("resume_text").asText());
            jsonObject.put("extracted_info", jsonNode.get("extracted_info").asText());
        }
        catch (Exception e) {
            logger.info("Failed to extract resume info.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .header("Reason", "Failed to extract resume info.")
                    .body(null);
        }

        List<String> questions = webClientService.getWebClient().post()
                .uri("/getAllQuestions")
                .retrieve()
                .bodyToFlux(String.class)
                .collectList().block();

        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = dateTime.format(formatter);
        List<Question> questionList = new ArrayList<>();
        for (String question : questions) {
            Question question1 = new Question();
            question1.setInterviewId(interview.getId());
            question1.setCreateDate(formattedDateTime);
            question1.setDescription(question);
            questionService.createQuestion(question1);
            questionList.add(question1);
        }

        return ResponseEntity.ok(questionList);
    }

    @Override
    public Interview getInterviewEvaluation(Interview interview) {
        webClientService.getWebClient().get()
                .uri("/getInterviewEvaluation")
                .retrieve()
                .bodyToMono(String.class)
                .block();

        return interview;
    }
}
