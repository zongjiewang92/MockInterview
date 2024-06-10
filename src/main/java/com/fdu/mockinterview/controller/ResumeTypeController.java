package com.fdu.mockinterview.controller;

import com.fdu.mockinterview.common.Result;
import com.fdu.mockinterview.common.ResultBuilder;
import com.fdu.mockinterview.entity.ResumeType;
import com.fdu.mockinterview.service.ResumeTypeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Result<List<ResumeType>>> getAllResumeTypes() {
        return ResponseEntity.ok(ResultBuilder.success(resumeTypeService.getAllResumeTypes()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Result<ResumeType>> getResumeTypeById(@PathVariable Integer id) {
        return ResponseEntity.ok(ResultBuilder.success(resumeTypeService.getResumeTypeById(id)));
    }

    @PostMapping("/createResumeType")
    public ResponseEntity<Result<ResumeType>> createResumeType(@RequestBody ResumeType resumeType) {
        return ResponseEntity.ok(ResultBuilder.success(resumeTypeService.createResumeType(resumeType)));
    }

    @PutMapping("/updateResumeType")
    public ResponseEntity<Result<ResumeType>> updateResumeType(@RequestBody ResumeType resumeType) {
        return ResponseEntity.ok(ResultBuilder.success(resumeTypeService.updateResumeType(resumeType)));
    }

    @DeleteMapping("/deleteResumeType/{id}")
    public ResponseEntity<Result<Integer>> deleteResumeType(@PathVariable Integer id) {
        resumeTypeService.deleteResumeType(id);
        return ResponseEntity.ok(ResultBuilder.success(id));
    }
}
