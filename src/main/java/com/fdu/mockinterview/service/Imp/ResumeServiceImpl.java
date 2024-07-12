package com.fdu.mockinterview.service.Imp;

import com.fdu.mockinterview.common.PageResult;
import com.fdu.mockinterview.common.ResultBuilder;
import com.fdu.mockinterview.entity.Resume;
import com.fdu.mockinterview.mapper.ResumeMapper;
import com.fdu.mockinterview.service.ResumeService;
import com.fdu.mockinterview.service.WebClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


@Service("resumeService")
public class ResumeServiceImpl implements ResumeService {

    private static final Logger logger = LoggerFactory.getLogger(ResumeService.class);

    private static final String UPLOAD_RESUME_DIR = "uploads/resumes/";

    private static final String DIR_SPLITER = "/";


    @Resource
    private WebClientService webClientService;  // this.webClient = WebClient.create("http://localhost:5000");

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
    public ResponseEntity<PageResult<List<Resume>>> selectByUserIdPages(int pageNum, int pageSize, Integer userId) {
        int offset = (pageNum - 1) * pageSize;
        List<Resume> resumeList = resumeMapper.selectByUserIdPages(offset, pageSize, userId);

        long totalElements = resumeMapper.countResumeByUserId(userId);
        int totalPages = (int) Math.ceil((double) totalElements / pageSize);

        return ResponseEntity.ok(ResultBuilder.paginatedSuccess(resumeList, pageNum, pageSize, totalElements, totalPages));

    }

    @Override
    public Resume getResumeById(Integer id) {
        return resumeMapper.selectByPrimaryKey(id);
    }

    @Override
    public Resume createResume(Resume resume) {
        resumeMapper.insert(resume);
        return resumeMapper.selectByPrimaryKey(resume.getId());
    }

    @Override
    public Resume updateResume(Resume resume) {
        resumeMapper.updateByPrimaryKey(resume);
        return resumeMapper.selectByPrimaryKey(resume.getId());
    }

    @Override
    public void deleteResume(Integer id) {
        resumeMapper.deleteByPrimaryKey(id);
    }

    @Override
    public ResponseEntity<String> uploadResumeFile(MultipartFile file, Integer resumeId, Integer fileType) {

        try {

            String originalFilename = file.getOriginalFilename();

            // Save file to the specified directory
            byte[] bytes = file.getBytes();

            Path uploadPath = Paths.get(UPLOAD_RESUME_DIR + resumeId + DIR_SPLITER);

            // create path
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            Resume resume = resumeMapper.selectByPrimaryKey(resumeId);
            resume.setCvDirectory(uploadPath.toString());

            assert originalFilename != null;
            Path filePath = uploadPath.resolve(originalFilename);
            Files.write(filePath, bytes);


            Files.write(filePath, bytes);

            // Update database
            resume.setCvName(originalFilename);
            resume.setCvType(fileType);

//            String cvContext = ai.getCvContext(file);
//            resume.setCvContext();
            String jsonResponse = webClientService.getWebClient().get()
                    .uri("/parseResumeFile?file_path=" + Paths.get(resume.getCvDirectory()).resolve(resume.getCvName()).normalize())
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
            resume.setCvContext(jsonResponse);

            resumeMapper.updateByPrimaryKey(resume);

            return ResponseEntity.status(HttpStatus.OK).body("File uploaded successfully: " + originalFilename);
        } catch (IOException e) {
            logger.info("Failed to upload file.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload file.");
        }

    }

    @Override
    public ResponseEntity<org.springframework.core.io.Resource> downloadResumeFile(Integer resumeId) {

        Resume resume = resumeMapper.selectByPrimaryKey(resumeId);

        try {
            Path filePath = Paths.get(resume.getCvDirectory()).resolve(resume.getCvName()).normalize();
            org.springframework.core.io.Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() && resource.isReadable()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_OCTET_STREAM)
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (Exception e) {

            logger.info("Download file exception:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

    }
}
