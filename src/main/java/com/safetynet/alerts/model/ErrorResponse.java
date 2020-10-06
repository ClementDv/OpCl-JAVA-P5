package com.safetynet.alerts.model;

import java.util.HashMap;
import java.util.Map;

public class ErrorResponse {

    private int status;
    private String code;
    private String message;
    private Map<String, Object> metadata;

    public ErrorResponse(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.metadata = new HashMap<>();
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, Object> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, Object> metadata) {
        this.metadata = metadata;
    }

    public ErrorResponse withMetadata(String key, Object value) {
        if (value == null) {
            this.metadata.remove(key);
        } else {
            this.metadata.put(key, value);
        }
        return this;
    }

    @Override
    public String toString() {
        return "ErrorResponse{" +
                "status=" + status +
                ", code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", metadata=" + metadata +
                '}';
    }
}
