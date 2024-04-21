package org.ls.tweetpoints.data.enums;

import lombok.Getter;

@Getter
public enum HttpErrors {
    SYSTEM_ERROR(500),
    BAD_REQUEST(400),
    UNPROCESSABLE_CONTENT(422);

    private final Integer code;

    HttpErrors(Integer code) {
        this.code = code;
    }

}
