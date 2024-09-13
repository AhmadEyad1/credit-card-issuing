package com.credit.application.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author ahmad.yousef <ahmadeyad.network@gmail.com>
 */
public interface FileUploader {

    String uploadFile(final MultipartFile file) throws IOException;
}