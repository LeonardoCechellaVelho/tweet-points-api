package org.ls.tweetpoints.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class AppError {
    private Integer code;
    private String message;
}
