package org.acme.rest.client;

public class MyException extends RuntimeException {
    private final int status;
    private final String errorInfo;

    MyException(final int status, final String errorInfo) {
        this.status = status;
        this.errorInfo = errorInfo;
    }

    public String getErrorInfo() {
        return errorInfo;
    }

    public int getStatus() {
        return status;
    }
}
