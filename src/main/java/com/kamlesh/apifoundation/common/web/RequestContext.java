package com.kamlesh.apifoundation.common.web;

public final class RequestContext {
    public static final String CORRELATION_ID_HEADER = "X-Correlation-Id";
    private static final ThreadLocal<String> CORRELATION_ID = new ThreadLocal<>();

    private RequestContext() {}

    public static void setCorrelationId(String correlationId) {
        CORRELATION_ID.set(correlationId);
    }

    public static String correlationId() {
        return CORRELATION_ID.get();
    }

    public static void clear() {
        CORRELATION_ID.remove();
    }
}
