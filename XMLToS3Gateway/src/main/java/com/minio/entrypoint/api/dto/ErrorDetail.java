package com.minio.entrypoint.api.dto;


import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class ErrorDetail {
  private String message;
  private String code;
  private String field;
}
