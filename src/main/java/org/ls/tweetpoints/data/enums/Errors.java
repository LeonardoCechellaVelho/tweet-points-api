package org.ls.tweetpoints.data.enums;

import lombok.Getter;

@Getter
public enum Errors {
    SYSTEM_ERROR(500),
    BAD_REQUEST(400),
    UNPROCESSABLE_CONTENT(422);

    private final Integer code;

    Errors(Integer code) {
        this.code = code;
    }

}
