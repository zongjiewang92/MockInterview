package com.fdu.mockinterview.controller;

import com.fdu.mockinterview.common.PageResult;
import com.fdu.mockinterview.common.Result;
import com.fdu.mockinterview.common.ResultBuilder;
import com.fdu.mockinterview.entity.Resume;
import com.fdu.mockinterview.service.ResumeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;


@RestController
@RequestMapping("/resume")
@Tag(name = "Resume Controller", description = "Resume management APIs")
public class ResumeController {

    @Resource
    private ResumeService resumeService;

    @GetMapping(value = "/getAllResumes")
    public ResponseEntity<Result<List<Resume>>> getAllResumes() {
        return ResponseEntity.ok(ResultBuilder.success(resumeService.getAllResumes()));
    }

    @GetMapping(value = "/getResumesByUserId/{userId}")
    public ResponseEntity<Result<List<Resume>>> getResumesByUserId(@PathVariable Integer userId) {
        return ResponseEntity.ok(ResultBuilder.success(resumeService.getAllResumesByUserId(userId)));
    }

    @GetMapping(value = "/selectByUserIdPages")
    public ResponseEntity<PageResult<List<Resume>>> selectByUserIdPages(@RequestParam(defaultValue = "1") int pageNum,
                                                                           @RequestParam(defaultValue = "10") int pageSize,
                                                                           @RequestParam int userId) {
        return resumeService.selectByUserIdPages(pageNum, pageSize, userId);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Result<Resume>> getResumeById(@PathVariable Integer id) {
        return ResponseEntity.ok(ResultBuilder.success(resumeService.getResumeById(id)));
    }

    @PostMapping("/createResume")
    public ResponseEntity<Result<Resume>> createResume(@RequestBody Resume Resume) {
        return ResponseEntity.ok(ResultBuilder.success(resumeService.createResume(Resume)));
    }

    @PutMapping("/updateResume")
    public ResponseEntity<Result<Resume>> updateResume(@RequestBody Resume Resume) {
        return ResponseEntity.ok(ResultBuilder.success(resumeService.updateResume(Resume)));
    }

    @DeleteMapping("/deleteResume/{id}")
    public ResponseEntity<Result<Integer>> deleteResume(@PathVariable Integer id) {
        resumeService.deleteResume(id);
        return ResponseEntity.ok(ResultBuilder.success(id));
    }


    @Operation(summary = "Upload a file")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "File uploaded successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    @PostMapping(value = "/uploadResume", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadFile(@RequestPart(name = "file") MultipartFile file,
                                             @RequestParam Integer resumeId,
                                             @RequestParam Integer fileType) {
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please select a file to upload.");
        }
        return resumeService.uploadResumeFile(file, resumeId, fileType);
    }


    @Operation(summary = "Upload a file")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "File uploaded successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    @PostMapping(value = "/uploadResume2", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadFile2(@RequestPart(name = "file") MultipartFile file,
                                             @RequestParam Integer userId) {
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please select a file to upload.");
        }
        Resume resume = new Resume();
        resume.setCvName(file.getOriginalFilename());
        resume.setUserId(userId);
        resumeService.createResume(resume);
        return resumeService.uploadResumeFile(file, resume.getId(), 0);
    }

    @GetMapping("/download/{resumeId:.+}")
    @Operation(summary = "Download a file")
    public ResponseEntity<org.springframework.core.io.Resource> downloadFile(@PathVariable Integer resumeId) {

        return resumeService.downloadResumeFile(resumeId);
    }



}
