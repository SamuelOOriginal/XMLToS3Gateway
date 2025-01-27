package com.minio.entrypoint.api.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class ErrorMessage {
  private String message;
  private String code;
  private ErrorDetail[] errors;
}
