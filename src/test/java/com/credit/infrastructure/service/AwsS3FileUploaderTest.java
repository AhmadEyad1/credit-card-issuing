package com.credit.infrastructure.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Utilities;
import software.amazon.awssdk.services.s3.model.GetUrlRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author ahmad.yousef <ahmadeyad.network@gmail.com>
 */
@SpringBootTest
@ActiveProfiles("test")
public class AwsS3FileUploaderTest {

    @Mock
    private S3Client s3Client;

    @Mock
    private S3Utilities s3Utilities;

    @InjectMocks
    private AwsS3FileUploader awsS3FileUploader;

    private static final String BUCKET_NAME = "bank-statements-test";
    private static final String FILE_URL = String.format("https://%s.s3.amazonaws.com/%s", BUCKET_NAME, UUID.randomUUID());

    @BeforeEach
    void setUp() throws MalformedURLException {
        awsS3FileUploader.s3BucketName = BUCKET_NAME;
        when(s3Client.utilities()).thenReturn(s3Utilities);
        when(s3Utilities.getUrl(any(GetUrlRequest.class))).thenReturn(URI.create(FILE_URL).toURL());
    }

    @Test
    void shouldUploadFileAndReturnUrl() throws IOException {
        final MockMultipartFile multipartFile = new MockMultipartFile("file", "test-file.txt", "text/plain", "Test file content".getBytes());

        when(s3Client.putObject(any(PutObjectRequest.class), any(RequestBody.class)))
                .thenReturn(PutObjectResponse.builder().build());

        final String fileUrl = awsS3FileUploader.uploadFile(multipartFile);

        assertEquals(FILE_URL, fileUrl, "File URL mismatch");
        verify(s3Client).putObject(any(PutObjectRequest.class), any(RequestBody.class));
        verify(s3Utilities).getUrl(any(GetUrlRequest.class));
    }
}