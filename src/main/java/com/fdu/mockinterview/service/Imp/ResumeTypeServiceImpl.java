package com.fdu.mockinterview.service.Imp;

import com.fdu.mockinterview.entity.ResumeType;
import com.fdu.mockinterview.mapper.ResumeTypeMapper;
import com.fdu.mockinterview.service.ResumeTypeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service("resumeTypeService")
public class ResumeTypeServiceImpl implements ResumeTypeService {

    @Resource
    private ResumeTypeMapper resumeTypeMapper;

    @Override
    public List<ResumeType> getAllResumeTypes() {
        return resumeTypeMapper.selectAll();
    }

    @Override
    public ResumeType getResumeTypeById(Integer id) {
        return resumeTypeMapper.selectByPrimaryKey(id);
    }

    @Override
    public ResumeType createResumeType(ResumeType resumeType) {
        resumeTypeMapper.insert(resumeType);
        return resumeTypeMapper.selectByPrimaryKey(resumeType.getId());
    }

    @Override
    public ResumeType updateResumeType(ResumeType resumeType) {
        resumeTypeMapper.updateByPrimaryKey(resumeType);
        return resumeTypeMapper.selectByPrimaryKey(resumeType.getId());
    }

    @Override
    public void deleteResumeType(Integer id) {
        resumeTypeMapper.deleteByPrimaryKey(id);
    }
}
