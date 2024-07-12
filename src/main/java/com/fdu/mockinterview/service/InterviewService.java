package com.fdu.mockinterview.service;

import com.fdu.mockinterview.common.PageResult;
import com.fdu.mockinterview.entity.Interview;
import com.fdu.mockinterview.entity.Question;
import org.apache.ibatis.annotations.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface InterviewService {

    List<Interview> getAllInterviews();

    List<Interview> getAllInterviewsByUserId(Integer userId);

    ResponseEntity<PageResult<List<Interview>>> getAllInterviewsByUserIdPages(@Param("offset") int offset, @Param("pageSize") int pageSize, Integer userId);

    Integer countInterviewByUserId(Integer userId);

    Interview getInterviewById(Integer id);

    Interview createInterview(Integer userId, Integer cvId, String companyName, String position);

    Interview updateInterview(Interview interview);

    void deleteInterview(Integer id);

    ResponseEntity<List<Question>> startInterview(Interview interview);

    Interview getInterviewEvaluation(Interview interview);
}
