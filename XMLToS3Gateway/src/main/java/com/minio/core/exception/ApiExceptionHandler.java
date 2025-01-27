package com.minio.core.exception;

import com.minio.entrypoint.api.dto.ErrorPresenter;
import com.minio.entrypoint.api.dto.Error;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestControllerAdvice
@RequiredArgsConstructor
public class ApiExceptionHandler {

    private static final String BUSINESS_ERROR = "error.business.request";
    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(FileUploadException.class)
    public ResponseEntity<ErrorPresenter> handleFileUploadException(final FileUploadException e) {
        LocalDateTime timeStamp = LocalDateTime.now();
        final var details = new Error(BUSINESS_ERROR, "O nome do arquivo está incorreto.");
        details.setUniqueId(UUID.randomUUID().toString());

        final var presenter = ErrorPresenter.builder()
                .error("Nome do arquivo incorreto")
                .timestamp(timeStamp.toString())
                .errors(new HashSet<>(Collections.singleton(details)))
                .transactionId(null)
                .status(BAD_REQUEST.value())
                .build();

        return ResponseEntity.status(BAD_REQUEST).body(presenter);
    }

    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ExceptionHandler(InternalServerException.class)
    public ResponseEntity<ErrorPresenter> handleInternalServerException(final InternalServerException e) {
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(getError(e, INTERNAL_SERVER_ERROR));
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ErrorPresenter> handleMaxSizeException(MaxUploadSizeExceededException exc) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(getError(exc, BAD_REQUEST));
    }

    private static ErrorPresenter getError(final Throwable t, final HttpStatus status) {
        final var errors = getErrorDetails(t);
        return ErrorPresenter.builder()
                .errors(errors)
                .status(status.value())
                .build();
    }

    private static Set<Error> getErrorDetails(final Throwable e) {
        final var details = new Error();
        details.setInformationCode(BUSINESS_ERROR);
        details.setMessage(e.getMessage());

        return new HashSet<>(Collections.singleton(details));
    }
}
