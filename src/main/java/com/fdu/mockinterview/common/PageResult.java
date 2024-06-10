package com.fdu.mockinterview.common;

import lombok.Data;

import java.util.List;

@Data
public class PageResult<T> extends Result<T> {
    private PageData pageData;

    public PageResult(int status, String message, T data, List<String> errors, PageData pageData) {
        super(status, message, data, errors);
        this.pageData = pageData;
    }

    // Getters and Setters
}
