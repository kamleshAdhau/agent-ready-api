package com.kamlesh.apifoundation.common.api;

import java.time.Instant;

public record ApiResponse<T>(
        String status,
        String message,
        T data,
        String correlationId,
        Instant timestamp
) {
    public static <T> ApiResponse<T> success(String message, T data, String correlationId) {
        return new ApiResponse<>("SUCCESS", message, data, correlationId, Instant.now());
    }
}
