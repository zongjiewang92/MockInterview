package com.fdu.mockinterview.service;

import com.fdu.mockinterview.common.Result;
import com.fdu.mockinterview.entity.Question;
import org.apache.ibatis.annotations.Param;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Repository
public interface QuestionService {

    List<Question> getAllQuestions();

    List<Question> getQuestionByUserIdPages(@Param("offset") int offset, @Param("pageSize") int pageSize, Integer userId);

    Question getQuestionById(Integer id);

    Question createQuestion(Question question);

    Question updateQuestion(Question question);

    void deleteQuestion(Integer id);

    long countQuestionByUserId(int interviewId);
    String uploadAnswerFile(MultipartFile file, Question question);
    ResponseEntity<Resource> downloadAnswerFile(Integer resumeId);

    ResponseEntity<Result<Question>> answerQuestion(MultipartFile file, Integer questionId, Integer nextQuestionId);

    void deleteQuestionsByInterviewId(Integer id);
}
