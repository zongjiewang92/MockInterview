package com.fdu.mockinterview.service;

import com.fdu.mockinterview.entity.Question;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

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
}
