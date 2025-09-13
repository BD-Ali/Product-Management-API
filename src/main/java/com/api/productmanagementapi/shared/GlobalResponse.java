package com.api.productmanagementapi.shared;

import lombok.Getter;

import java.util.List;


@Getter
public class GlobalResponse<T> {
    private final String status;
    private final T data;
    private final List<ErrorItem> errors;

    public record ErrorItem(String message) {}

    public GlobalResponse(List<ErrorItem> errors) {
        this.status = "error";
        this.errors = errors;
        this.data = null;
    }
    public GlobalResponse(T data) {
        this.status = "success";
        this.data = data;
        this.errors = null;
    }

}

