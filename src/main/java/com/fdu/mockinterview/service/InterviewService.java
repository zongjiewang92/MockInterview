package com.fdu.mockinterview.service;

import com.fdu.mockinterview.entity.Interview;
import com.fdu.mockinterview.entity.Resume;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface InterviewService {

    List<Interview> getAllInterviews();

    List<Interview> getAllInterviewsByUserId(Integer userId);

    List<Interview> getAllInterviewsByUserIdPages(@Param("offset") int offset, @Param("pageSize") int pageSize, Integer userId);

    Interview getInterviewById(Integer id);

    Interview createInterview(Interview interview);

    Interview updateInterview(Interview interview);

    void deleteInterview(Integer id);
}
