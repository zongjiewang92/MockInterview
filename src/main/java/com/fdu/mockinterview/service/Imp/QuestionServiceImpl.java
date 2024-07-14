package com.fdu.mockinterview.service.Imp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fdu.mockinterview.common.Constant;
import com.fdu.mockinterview.entity.Question;
import com.fdu.mockinterview.mapper.QuestionMapper;
import com.fdu.mockinterview.service.QuestionService;
import com.fdu.mockinterview.service.WebClientService;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.UrlResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


@Service("questionService")
public class QuestionServiceImpl implements QuestionService {
    private static final Logger logger = LoggerFactory.getLogger(QuestionService.class);
    private static final String UPLOAD_RESUME_DIR = "uploads/questions/";

    private static final String DIR_SPLITER = "/";

    @Resource
    private QuestionMapper questionMapper;
    @Resource
    private WebClientService webClientService;

    @Override
    public List<Question> getAllQuestions() {
        return questionMapper.selectAll();
    }


    public List<Question> getQuestionByUserIdPages(int pageNum, int pageSize, Integer interviewId) {
        int offset = (pageNum - 1) * pageSize;
        return questionMapper.selectQuestionByInterviewIdPages(offset, pageSize, interviewId);
    }
    @Override
    public Question getQuestionById(Integer id) {
        return questionMapper.selectByPrimaryKey(id);
    }

    @Override
    public Question createQuestion(Question question) {
        questionMapper.insert(question);
        return questionMapper.selectByPrimaryKey(question.getId());
    }

    @Override
    public Question updateQuestion(Question question) {
        questionMapper.updateByPrimaryKey(question);
        return questionMapper.selectByPrimaryKey(question.getId());
    }

    @Override
    public void deleteQuestion(Integer id) {
        questionMapper.deleteByPrimaryKey(id);
    }

    @Override
    public long countQuestionByUserId(int interviewId) {
        return questionMapper.countQuestionByUserId(interviewId);
    }

    @Override
    public String uploadAnswerFile(MultipartFile file, Question question) {
        try {

            Integer questionsId = question.getId();
            Integer interviewId = question.getInterviewId();
            String originalFilename = file.getOriginalFilename();

            // Save file to the specified directory
            byte[] bytes = file.getBytes();

            Path uploadPath = Paths.get(UPLOAD_RESUME_DIR + interviewId + DIR_SPLITER + questionsId + DIR_SPLITER);

            // create path
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            question.setAnswerDirectory(uploadPath.toString());

            assert originalFilename != null;
            Path filePath = uploadPath.resolve(originalFilename);
            Files.write(filePath, bytes);


            Files.write(filePath, bytes);

            // Update database
            question.setAnswerName(originalFilename);

            questionMapper.updateByPrimaryKey(question);

            return filePath.toString();
        } catch (IOException e) {
            logger.info("Failed to upload file.", e);
            return null;
        }
    }

    @Override
    public ResponseEntity<org.springframework.core.io.Resource> downloadAnswerFile(Integer questionId) {
        Question question = questionMapper.selectByPrimaryKey(questionId);

        try {
            Path filePath = Paths.get(question.getAnswerDirectory()).resolve(question.getAnswerName()).normalize();
            org.springframework.core.io.Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() && resource.isReadable()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_OCTET_STREAM)
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (Exception e) {

            logger.info("Download file exception:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Override
    public ResponseEntity<String> answerQuestion(MultipartFile file, Integer questionId) {

        Question question = questionMapper.selectByPrimaryKey(questionId);

        if (question==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        // upload answer media file
        String filePathName = uploadAnswerFile(file, question);
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode jsonObject = objectMapper.createObjectNode();
        jsonObject.put("user_input", filePathName);
        jsonObject.put(Constant.INTERVIEW_ID, question.getInterviewId());

        // call AI service and return result
        ResponseEntity<String> responseEntity = webClientService.getWebClient().post()
                .uri("/service")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(jsonObject)
                .retrieve()
                .toEntity(String.class)
                .block();


        if (responseEntity != null) {
            HttpStatusCode statusCode = responseEntity.getStatusCode();
            String responseBody = responseEntity.getBody();

            if (statusCode.is2xxSuccessful()) {
                System.out.println("Success! Status Code: " + statusCode);
                System.out.println("Response Body: " + responseBody);
                return ResponseEntity.ok("Success!");

            } else {
                System.err.println("Error! Status Code: " + statusCode);
                System.err.println("Response Body: " + responseBody);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ai service api error!");
            }
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ai service api error!");
        }
    }
}
