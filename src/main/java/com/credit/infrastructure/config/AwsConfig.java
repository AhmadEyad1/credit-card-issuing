package com.credit.infrastructure.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

/**
 * @author ahmad.yousef <ahmadeyad.network@gmail.com>
 */
@Configuration
public class AwsConfig {

    @Value("${aws.bucket.region}")
    private String s3BucketRegion;

    @Bean
    public S3Client s3Client() {
        return S3Client.builder()
                .region(Region.of(s3BucketRegion))
                .build();
    }

}
