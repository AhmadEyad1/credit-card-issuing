package com.credit.infrastructure.service;

import com.credit.application.service.FileUploader;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetUrlRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;
import java.util.UUID;

/**
 * @author ahmad.yousef <ahmadeyad.network@gmail.com>
 */
@RequiredArgsConstructor
public class AwsS3FileUploader implements FileUploader {

    @Value("${aws.bucket.name}")
    public String s3BucketName; // set public for testing, since class is not annotated with @Component

    private final S3Client client;

    @Override
    public String uploadFile(final MultipartFile multipartFile) throws IOException {
        final File file = convertMultiPartToFile(multipartFile);

        final String fileId = UUID.randomUUID().toString();
        final PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(s3BucketName)
                .key(fileId)
                .build();

        client.putObject(putObjectRequest, RequestBody.fromBytes(Files.readAllBytes(file.toPath())));

        final GetUrlRequest getUrlRequest =  GetUrlRequest.builder().bucket(s3BucketName).key(fileId).build();
        return client.utilities().getUrl(getUrlRequest).toString();
    }

    private File convertMultiPartToFile(final MultipartFile multipartFile) throws IOException {
        final File file = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        multipartFile.transferTo(file);
        return file;
    }
}
