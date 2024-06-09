package com.fdu.mockinterview.service;

import com.fdu.mockinterview.entity.Interview;
import com.fdu.mockinterview.entity.Question;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface QuestionService {

    List<Question> getAllQuestions();

    List<Question> getAllQuestionByUserIdPages(@Param("offset") int offset, @Param("pageSize") int pageSize, Integer userId);

    Question getQuestionById(Integer id);

    Question createQuestion(Question question);

    Question updateQuestion(Question question);

    void deleteQuestion(Integer id);
}
