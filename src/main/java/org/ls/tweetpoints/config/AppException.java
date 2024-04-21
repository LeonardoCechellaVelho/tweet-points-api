package org.ls.tweetpoints.config;

import org.ls.tweetpoints.data.enums.Errors;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@AllArgsConstructor
@Builder
@Getter
@Setter
public class AppException extends RuntimeException {
    private Integer httpError;
    private String code;
    private String message;

    public AppException(Integer httpError, Errors error) {
        this.httpError = httpError;
        this.code = error.getCode();
        this.message = error.getMessage();
    }
}
