package com.api.productmanagementapi.shared;
import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public class CustomResponseException extends RuntimeException{
    private int code;
    private String message;

    public static CustomResponseException ResourceNotFound(String message) {
        return new CustomResponseException(404, message);
    }
    public static CustomResponseException BadRequest(String message) {
        return new CustomResponseException(400, message);
    }
    public static CustomResponseException Conflict(String message) {
        return new CustomResponseException(409, message);
    }
    public static CustomResponseException InternalServerError(String message) {
        return new CustomResponseException(500, message);
    }

}
