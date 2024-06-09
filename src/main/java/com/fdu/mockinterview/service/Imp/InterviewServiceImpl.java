package com.fdu.mockinterview.service.Imp;

import com.fdu.mockinterview.entity.Interview;
import com.fdu.mockinterview.entity.Resume;
import com.fdu.mockinterview.mapper.InterviewMapper;
import com.fdu.mockinterview.service.InterviewService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service("interviewService")
public class InterviewServiceImpl implements InterviewService {

    @Resource
    private InterviewMapper interviewMapper;

    @Override
    public List<Interview> getAllInterviews() {
        return interviewMapper.selectAll();
    }

    @Override
    public List<Interview> getAllInterviewsByUserId(Integer userId) {
        return interviewMapper.selectInterviewByUserId(userId);
    }

    @Override
    public List<Interview> getAllInterviewsByUserIdPages(int pageNum, int pageSize, Integer userId) {
        int offset = (pageNum - 1) * pageSize;
        return interviewMapper.selectInterviewByUserIdPages(offset, pageSize, userId);
    }

    @Override
    public Interview getInterviewById(Integer id) {
        return interviewMapper.selectByPrimaryKey(id);
    }

    @Override
    public Interview createInterview(Interview interview) {
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
}
