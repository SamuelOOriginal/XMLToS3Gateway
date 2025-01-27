package com.minio.dataprovider.filesystem;

import com.amazonaws.AmazonClientException;
import com.amazonaws.SdkClientException;
import com.minio.core.common.LogGenerator;
import com.minio.core.config.MinioProperties;
import com.minio.core.exception.FileUploadException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static com.minio.core.common.Constants.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3FileSystem implements BucketClient {
    private static final String UPLOAD_ERROR = "Não foi possível fazer o upload do arquivo";
    private final MinioProperties properties;
    private final LogGenerator logGenerator;

    private final S3Client ioClient;

    public void uploadListFileFromRequestAnt(List<MultipartFile> multipartFile) {
        for(MultipartFile file : multipartFile){
            final var fileName = S3_IN_PATH + file.getOriginalFilename();

            try {
                final var is = file.getInputStream();
                final var request = PutObjectRequest.builder()
                        .bucket(properties.getBucket())
                        .key(fileName)
                        .build();

                ioClient.putObject(request, RequestBody.fromInputStream(is, file.getSize()));
                log.info(logGenerator.logMsg(file.getName(), "Arquivo enviado"));
            } catch (AmazonClientException | IOException | S3Exception e) {
                log.error(logGenerator.errorMsg(file.getName(), UPLOAD_ERROR, e));
                throw new FileUploadException(UPLOAD_ERROR);
            }
        }
    }
    @Override
    public void uploadListFileFromRequest(List<MultipartFile> multipartFile) {
        CompletableFuture.allOf(multipartFile.stream()
                .map(file -> CompletableFuture.runAsync(() -> {
                    final var fileName = S3_IN_PATH + file.getOriginalFilename();

                    try {
                        final var is = file.getInputStream();
                        final var request = PutObjectRequest.builder()
                                .bucket(properties.getBucket())
                                .key(fileName)
                                .build();

                        ioClient.putObject(request, RequestBody.fromInputStream(is, file.getSize()));
                        log.info(logGenerator.logMsg(file.getName(), "Arquivo enviado"));
                    } catch (AmazonClientException | IOException | S3Exception e) {
                        log.error(logGenerator.errorMsg(file.getName(), UPLOAD_ERROR, e));
                        throw new FileUploadException(UPLOAD_ERROR);
                    }
                })).toArray(CompletableFuture[]::new)).join();
    }
}
