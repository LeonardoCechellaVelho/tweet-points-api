package org.ls.tweetpoints.validators;

import org.ls.tweetpoints.config.AppException;
import org.ls.tweetpoints.data.enums.Errors;

public class EmptyValidator {
    public static <T> void isValid(T field, String fieldName) {
        if (field == null || (field instanceof String && ((String) field).isEmpty())) {
            throw new AppException(Errors.UNPROCESSABLE_CONTENT.getCode(), fieldName + " cannot be null or empty");
        }
    }
}
