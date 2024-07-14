package com.fdu.mockinterview.service.Imp;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fdu.mockinterview.common.Constant;
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
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;


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

        interviewMapper.insert(interview);
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
    public ResponseEntity<List<Question>> startInterview(Interview interviewInput) {

        questionService.deleteQuestionsByInterviewId(interviewInput.getId());

        Interview interview = interviewMapper.selectByPrimaryKey(interviewInput.getId());

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode jsonObject = objectMapper.createObjectNode();
        jsonObject.put("company", interview.getCompanyName());
        jsonObject.put("position", interview.getPosition());

        Resume resume = resumeService.getResumeById(interview.getCvId());
        String cvContext = resume.getCvContext();

        String  interviewId = interview.getId().toString();
        jsonObject.put(Constant.INTERVIEW_ID, interviewId);
        Constant.sessionMap.put(interviewId, interviewId);

        try {
            JsonNode jsonNode = objectMapper.readTree(cvContext);
            jsonObject.put("resume_text", jsonNode.get("resume_text").asText());
            jsonObject.put("extracted_info", jsonNode.get("extracted_info"));
        }
        catch (Exception e) {
            logger.info("Failed to extract resume info.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .header("Reason", "Failed to extract resume info.")
                    .body(null);
        }

        String firstQuestionPath = webClientService.getWebClient().post()
                .uri("/initialize")
                .bodyValue(jsonObject)
                .retrieve()
                .onStatus(HttpStatusCode::isError, response -> {
                    // print response code
                    System.err.println("Error response code: " + response.statusCode());
                    return Mono.error(new RuntimeException("HTTP " + response.statusCode()));
                })
                .bodyToMono(String.class)
                .block();

        assert firstQuestionPath != null;
        firstQuestionPath = firstQuestionPath.substring(1, firstQuestionPath.lastIndexOf("\""));
        Path path = Paths.get(firstQuestionPath);
        Path normalizedPath = path.normalize();
        String normalizedString = normalizedPath.toString();


        List<String> questions = webClientService.getWebClient().post()
                .uri("/getAllQuestions")
                .bodyValue(jsonObject)
                .retrieve()
                .onStatus(HttpStatusCode::isError, response -> {
                    // print response code
                    System.err.println("Error response code: " + response.statusCode());
                    return Mono.error(new RuntimeException("HTTP " + response.statusCode()));
                })
                .bodyToFlux(String.class)
                .collectList()
                .block();



        if (questions==null || questions.isEmpty()){
            return ResponseEntity.ok(new ArrayList<>());
        }

        List<Question> questionList = new ArrayList<>();
        int number = 1;
        for (String desc : questions) {

            if (desc.equalsIgnoreCase("[") || desc.equalsIgnoreCase("]")){
                continue;
            }else {
                desc = desc.trim();
                if (desc.startsWith("\"")){
                    desc = desc.substring(1);
                }
                if (desc.endsWith(",")){
                    desc = desc.substring(0, desc.lastIndexOf(","));
                }
                if (desc.endsWith("\"")){
                    desc = desc.substring(0, desc.lastIndexOf("\""));
                }
            }

            Question question = new Question();
            question.setInterviewId(interview.getId());
            question.setNumber(number);
            if (number==1){
                question.setQuestionDirectory(normalizedString);
            }
            question.setDescription(desc);
            questionService.createQuestion(question);
            questionList.add(question);
            number++;
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
