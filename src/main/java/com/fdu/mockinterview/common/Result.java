package com.fdu.mockinterview.common;

import java.util.List;

public class Result<T> {
    private int status;
    private String message;
    private T data;
    private List<String> errors;

    public Result() {
    }

    public Result(int status, String message, T data, List<String> errors) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.errors = errors;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}
