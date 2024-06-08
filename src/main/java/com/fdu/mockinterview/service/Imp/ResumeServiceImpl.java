package com.fdu.mockinterview.service.Imp;

import com.fdu.mockinterview.entity.Resume;
import com.fdu.mockinterview.entity.User;
import com.fdu.mockinterview.mapper.ResumeMapper;
import com.fdu.mockinterview.service.ResumeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service("resumeService")
public class ResumeServiceImpl implements ResumeService {

    @Resource
    private ResumeMapper resumeMapper;

    @Override
    public List<Resume> getAllResumes() {
        return resumeMapper.selectAll();
    }

    @Override
    public List<Resume> getAllResumesByUserId(Integer userId) {
        return resumeMapper.selectAllByUserId(userId);
    }

    @Override
    public List<Resume> selectAllByUserIdPages(int pageNum, int pageSize, Integer userId) {
        int offset = (pageNum - 1) * pageSize;
        return resumeMapper.selectAllByUserIdPages(offset, pageSize, userId);
    }

    @Override
    public Resume getResumeById(Integer id) {
        return resumeMapper.selectByPrimaryKey(id);
    }

    @Override
    public Resume createResume(Resume resume) {
        int id = resumeMapper.insert(resume);
        return resumeMapper.selectByPrimaryKey(id);
    }

    @Override
    public Resume updateResume(Resume resume) {
        int id = resumeMapper.updateByPrimaryKey(resume);
        return resumeMapper.selectByPrimaryKey(id);
    }

    @Override
    public void deleteResume(Integer id) {
        resumeMapper.deleteByPrimaryKey(id);
    }

}
