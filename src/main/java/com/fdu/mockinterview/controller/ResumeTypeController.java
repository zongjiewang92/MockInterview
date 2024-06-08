package com.fdu.mockinterview.controller;

import com.fdu.mockinterview.entity.ResumeType;
import com.fdu.mockinterview.service.ResumeTypeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


@RestController
@RequestMapping("/resumeType")
@Tag(name = "ResumeType Controller", description = "ResumeType management APIs")
public class ResumeTypeController {

    @Resource
    private ResumeTypeService resumeTypeService;

    @GetMapping(value = "/getAllResumeTypes")
    public List<ResumeType> getAllResumeTypes() {
        return resumeTypeService.getAllResumeTypes();
    }

    @GetMapping("/{id}")
    public ResumeType getResumeTypeById(@PathVariable Integer id) {
        return resumeTypeService.getResumeTypeById(id);
    }

    @PostMapping("/createResumeType")
    public ResumeType createResumeType(@RequestBody ResumeType resumeType) {
        return resumeTypeService.createResumeType(resumeType);
    }

    @PutMapping("/updateResumeType")
    public ResumeType updateResumeType(@RequestBody ResumeType resumeType) {
        return resumeTypeService.updateResumeType(resumeType);
    }

    @DeleteMapping("/deleteResumeType/{id}")
    public void deleteResumeType(@PathVariable Integer id) {
        resumeTypeService.deleteResumeType(id);
    }
}
