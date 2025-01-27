package com.minio.entrypoint.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.springframework.http.MediaType.*;

public interface MinioUploadXMLController {
    @Operation(summary = "Upload de múltiplos arquivos regulatórios E-Financeira")
    @PostMapping(value = "/upload-efinance-file",
            consumes = MULTIPART_FORM_DATA_VALUE,
            produces = APPLICATION_JSON_VALUE)
    @ApiResponse(responseCode = "200", description = "Upload efetuado com sucesso")
    ResponseEntity<Void> uploadFileEfinance(
            @RequestPart final List<MultipartFile> files,
            @RequestHeader(name = "requesting-ms") final String apiCaller);


    @GetMapping(value = "/valid",
            consumes = ALL_VALUE,
            produces = ALL_VALUE)
    @ApiResponse(responseCode = "200", description = "Validado")
    ResponseEntity<String> valid( @RequestHeader(name = "requesting-ms") final String apiCaller);

}