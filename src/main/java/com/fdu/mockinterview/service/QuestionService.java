package com.fdu.mockinterview.service;

import com.fdu.mockinterview.entity.Question;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface QuestionService {

    List<Question> getAllQuestions();

    Question getQuestionById(Integer id);

    Question createQuestion(Question question);

    Question updateQuestion(Question question);

    void deleteQuestion(Integer id);
}
