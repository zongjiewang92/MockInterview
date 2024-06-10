package com.fdu.mockinterview.common;

import java.util.List;

public class ResultBuilder {
    
    public static <T> Result<T> success(T data) {
        return new Result<>(200, "Success", data, null);
    }

    public static <T> Result<T> success(String message, T data) {
        return new Result<>(200, message, data, null);
    }

    public static <T> Result<T> error(int status, String message, List<String> errors) {
        return new Result<>(status, message, null, errors);
    }

    public static <T> Result<T> error(String message, List<String> errors) {
        return new Result<>(400, message, null, errors);
    }

    public static <T> PageResult<T> paginatedSuccess(T data, int page, int size, long totalElements, int totalPages) {
        PageData pageMetadata = new PageData(page, size, totalElements, totalPages);
        return new PageResult<>(200, "Success", data, null, pageMetadata);
    }


}
