package com.fdu.mockinterview.service;

import com.fdu.mockinterview.entity.ResumeType;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ResumeTypeService {

    List<ResumeType> getAllResumeTypes();

    ResumeType getResumeTypeById(Integer id);

    ResumeType createResumeType(ResumeType ResumeType);

    ResumeType updateResumeType(ResumeType ResumeType);

    void deleteResumeType(Integer id);
}
