package com.kamlesh.apifoundation.common.api;

import java.time.Instant;
import java.util.List;

public record ErrorResponse(
        String status,
        String code,
        String message,
        List<FieldErrorDetail> errors,
        String correlationId,
        Instant timestamp
) {
    public static ErrorResponse of(String code, String message, List<FieldErrorDetail> errors, String correlationId) {
        return new ErrorResponse("ERROR", code, message, errors, correlationId, Instant.now());
    }
}
