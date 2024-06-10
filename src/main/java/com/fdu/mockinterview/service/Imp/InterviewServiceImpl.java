package com.fdu.mockinterview.service.Imp;

import com.fdu.mockinterview.common.PageResult;
import com.fdu.mockinterview.common.ResultBuilder;
import com.fdu.mockinterview.entity.Interview;
import com.fdu.mockinterview.entity.Resume;
import com.fdu.mockinterview.mapper.InterviewMapper;
import com.fdu.mockinterview.service.InterviewService;
import org.springframework.http.ResponseEntity;
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
