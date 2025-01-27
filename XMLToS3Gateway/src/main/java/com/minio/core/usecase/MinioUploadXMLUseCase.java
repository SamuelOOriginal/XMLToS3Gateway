package com.minio.core.usecase;

import com.minio.core.common.LogGenerator;
import com.minio.core.config.MinioProperties;
import com.minio.dataprovider.filesystem.BucketClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MinioUploadXMLUseCase {
    private final MinioProperties minioProperties;
    private final LogGenerator logGenerator;
    private final BucketClient s3Client;
    public void uploadXML(List<MultipartFile> files) {
        log.info("Iniciando upload de {} arquivos...", files.size());

        for(MultipartFile file : files){
            try {
                log.info(logGenerator.logMsg(file.getName(), "Iniciando upload do arquivo..."));

                s3Client.uploadListFileFromRequest(files);

                log.info(logGenerator.logMsg(file.getName(), "Arquivo enviado"));
            } catch (Exception e) {
                log.error("Erro ao enviar arquivo {}", file.getOriginalFilename(), e);
            }
        }
    }

    public void validProp() {
        System.out.println("Uploading XML to Minio");
        System.out.println("host: " + minioProperties.getHost());
        System.out.println("accessKey: " + minioProperties.getAccessKey());
        System.out.println("secretKey: " + minioProperties.getSecretKey());
        System.out.println("region: " + minioProperties.getRegion());
        System.out.println("bucket: " + minioProperties.getBucket());

    }

}
