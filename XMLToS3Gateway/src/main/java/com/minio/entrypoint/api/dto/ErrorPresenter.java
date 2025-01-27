package com.minio.entrypoint.api.dto;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class ErrorPresenter {
  private String timestamp;
  private Integer status;
  private String error;
  private String path;
  private String transactionId;
  private Set<Error> errors = new HashSet<>();

  public ErrorPresenter(Error apiError, HttpStatus status, String path) {
    errors.add(apiError);
    this.status = status.value();
    this.path = path;
  }
}