package com.fdu.mockinterview.service;

import com.fdu.mockinterview.entity.Interview;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface InterviewService {

    List<Interview> getAllInterviews();

    Interview getInterviewById(Integer id);

    Interview createInterview(Interview interview);

    Interview updateInterview(Interview interview);

    void deleteInterview(Integer id);
}
