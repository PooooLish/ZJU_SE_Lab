package com.backend.academicsys.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Result<T> {
    private enum ResultCode {
        SUCCESS(200), FAIL(400), UNAUTHORIZED(401), NOT_FOUND(404), INTERNAL_SERVER_ERROR(500);

        private final Integer code;
        ResultCode(Integer code) {
            this.code = code;
        }
    }
    private Integer code;
    private String msg;
    private T data;

    public static <T> Result<T> success(T data) {
        return new Result<>(ResultCode.SUCCESS.code, "success", data);
    }
    public static <T> Result<T> success() {
        return new Result<>(ResultCode.SUCCESS.code, "success", null);
    }

    public static <T> Result<T> fail(String msg) {
        return new Result<>(ResultCode.FAIL.code, msg, null);
    }

    public static <T> Result<T> fail(String msg, T data) {
        return new Result<>(ResultCode.FAIL.code, msg, data);
    }
}

