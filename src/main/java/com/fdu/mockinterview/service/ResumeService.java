package com.fdu.mockinterview.service;

import com.fdu.mockinterview.common.PageResult;
import com.fdu.mockinterview.entity.Resume;
import org.apache.ibatis.annotations.Param;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Repository
public interface ResumeService {

    List<Resume> getAllResumes();
    List<Resume> getAllResumesByUserId(Integer userId);

    ResponseEntity<PageResult<List<Resume>>> selectByUserIdPages(@Param("offset") int offset, @Param("pageSize") int pageSize, Integer userId);

    Resume getResumeById(Integer id);

    Resume createResume(Resume user);

    Resume updateResume(Resume user);

    void deleteResume(Integer id);


    ResponseEntity<String> uploadResumeFile(MultipartFile file, Integer resumeId, Integer fileType);


    ResponseEntity<Resource> downloadResumeFile(Integer resumeId);
}
