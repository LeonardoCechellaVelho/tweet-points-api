package org.ls.tweetpoints.config;

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
    private Integer error;
    private String message;
}
