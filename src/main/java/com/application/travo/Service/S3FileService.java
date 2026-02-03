package com.application.travo.Service;

import com.application.travo.Utility.FileUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.io.IOException;

@Service
public class S3FileService {

    @Value("${aws.s3.bucket}")
    private String bucket;

    private final S3Client s3Client;

    public S3FileService(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    public String upload(MultipartFile file, String folder) {

        try {
            if (file.isEmpty()) {
                throw new RuntimeException("File is empty");
            }

            if (file.getSize() > 5 * 1024 * 1024) {
                throw new RuntimeException("File size exceeds 5MB");
            }

            String contentType = file.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                throw new RuntimeException("Only image files allowed");
            }

            String fileName = FileUtil.generateFileName(file.getOriginalFilename());
            String key = folder + "/" + fileName;

            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(bucket)
                    .key(key)
                    .contentType(contentType)
                    .build();

            s3Client.putObject(
                    request,
                    RequestBody.fromInputStream(
                            file.getInputStream(),
                            file.getSize()
                    )
            );

            return key; // ✅ return key, not URL

        } catch (S3Exception e) {
            System.err.println("🔥 S3 ERROR: " + e.awsErrorDetails().errorMessage());
            throw new RuntimeException("S3 upload failed");

        } catch (IOException e) {
            throw new RuntimeException("File read failed");

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
