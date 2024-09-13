package com.credit.infrastructure.service;

import com.credit.application.service.FileUploader;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author ahmad.yousef <ahmadeyad.network@gmail.com>
 */
@Component
public class AwsS3FileUploaderMock implements FileUploader {

    @Override
    public String uploadFile(final MultipartFile file) {
        return "https://bucket.s3.amazonaws.com/test_file";
    }
}
