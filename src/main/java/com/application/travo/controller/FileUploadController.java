package com.application.travo.controller;

import com.application.travo.Service.S3FileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/files")
public class FileUploadController {

    private final S3FileService fileService;

    public FileUploadController(S3FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/upload")
    public ResponseEntity<Map<String, String>> uploadFile(
            @RequestParam MultipartFile file,
            @RequestParam(defaultValue = "documents") String type
    ) throws IOException {

        String key = fileService.upload(file, type.toLowerCase());

        Map<String, String> response = new HashMap<>();
        response.put("s3Key", key);

        return ResponseEntity.ok(response);
    }
}
