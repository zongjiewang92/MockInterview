package com.fdu.mockinterview.controller;

import com.fdu.mockinterview.entity.Resume;
import com.fdu.mockinterview.service.ResumeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


@RestController
@RequestMapping("/resume")
@Tag(name = "Resume Controller", description = "Resume management APIs")
public class ResumeController {

    @Resource
    private ResumeService resumeService;

    @GetMapping(value = "/getAllResumes")
    public List<Resume> getAllResumes() {
        return resumeService.getAllResumes();
    }

    @GetMapping(value = "/getAllResumesByUserId/{userId}")
    public List<Resume> getAllResumesByUserId(@PathVariable Integer userId) {
        return resumeService.getAllResumesByUserId(userId);
    }

    @GetMapping(value = "/selectByUserIdPages")
    public List<Resume> selectAllByUserIdPages(@RequestParam(defaultValue = "1") int pageNum,
                                               @RequestParam(defaultValue = "10") int pageSize,
                                               @RequestParam int userId) {
        return resumeService.selectAllByUserIdPages(pageNum, pageSize, userId);
    }

    @GetMapping("/{id}")
    public Resume getResumeById(@PathVariable Integer id) {
        return resumeService.getResumeById(id);
    }

    @PostMapping("/createResume")
    public Resume createResume(@RequestBody Resume Resume) {
        return resumeService.createResume(Resume);
    }

    @PutMapping("/updateResume")
    public Resume updateResume(@RequestBody Resume Resume) {
        return resumeService.updateResume(Resume);
    }

    @DeleteMapping("/deleteResume/{id}")
    public void deleteResume(@PathVariable Integer id) {
        resumeService.deleteResume(id);
    }
}
