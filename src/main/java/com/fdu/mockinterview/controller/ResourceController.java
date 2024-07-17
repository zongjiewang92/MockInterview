package com.fdu.mockinterview.controller;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fdu.mockinterview.common.ResultBuilder;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

@RestController
@RequestMapping("/resource")
public class ResourceController {

    @GetMapping("/getResourceUrl")
    public ResponseEntity<Resource> getResourceUrl(@RequestParam String resourcePath) {
        System.out.println("getResourceUrl called with resourcePath: " + resourcePath);

        if (resourcePath == null || resourcePath.isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }

        try {
            String projectRoot = System.getProperty("user.dir");

            Path path = Paths.get(projectRoot, resourcePath);
            UrlResource resource = new UrlResource(path.toUri());

            if (resource.exists() && resource.isReadable()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_OCTET_STREAM)
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (MalformedURLException e) {
            return ResponseEntity.status(500).body(null);
        }
    }


}
