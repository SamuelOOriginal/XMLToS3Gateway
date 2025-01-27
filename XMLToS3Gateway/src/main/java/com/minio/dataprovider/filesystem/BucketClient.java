package com.minio.dataprovider.filesystem;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BucketClient {
    void uploadListFileFromRequest(final List<MultipartFile> multipartFile);
}
