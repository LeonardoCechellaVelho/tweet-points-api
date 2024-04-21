package org.ls.tweetpoints.validators;

import java.util.regex.Pattern;

import org.ls.tweetpoints.config.AppException;
import org.ls.tweetpoints.data.enums.Errors;
import org.ls.tweetpoints.data.enums.HttpErrors;

public class EmailValidator {

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
            "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
    );

    public static void isValid(String email) {
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new AppException(HttpErrors.UNPROCESSABLE_CONTENT.getCode(), Errors.EMAIL_INVALID);
        }
    }
}
