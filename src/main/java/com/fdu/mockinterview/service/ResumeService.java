package com.fdu.mockinterview.service;

import com.fdu.mockinterview.entity.Resume;
import com.fdu.mockinterview.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ResumeService {

    List<Resume> getAllResumes();
    List<Resume> getAllResumesByUserId(Integer userId);

    List<Resume> selectAllByUserIdPages(@Param("offset") int offset, @Param("pageSize") int pageSize, Integer userId);

    Resume getResumeById(Integer id);

    Resume createResume(Resume user);

    Resume updateResume(Resume user);

    void deleteResume(Integer id);
}
