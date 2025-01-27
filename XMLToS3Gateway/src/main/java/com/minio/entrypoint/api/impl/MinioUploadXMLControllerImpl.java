package com.minio.entrypoint.api.impl;

import com.minio.entrypoint.api.controller.MinioUploadXMLController;
import com.minio.core.usecase.MinioUploadXMLUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
@RestController
@RequiredArgsConstructor
public class MinioUploadXMLControllerImpl implements MinioUploadXMLController {

    private final MinioUploadXMLUseCase minioUploadXMLUseCase;
    @Override
    public ResponseEntity<Void> uploadFileEfinance(List<MultipartFile> files, String apiCaller) {

        minioUploadXMLUseCase.uploadXML(files);

        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<String> valid(String apiCaller) {

        minioUploadXMLUseCase.validProp();
        return ResponseEntity.ok("Validado");
    }
}
