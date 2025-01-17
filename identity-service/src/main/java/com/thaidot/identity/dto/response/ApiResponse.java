package com.thaidot.identity.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Deprecated
public class ApiResponse<T> {
    private String message;
    private int httpCode;
    private T result;
}
