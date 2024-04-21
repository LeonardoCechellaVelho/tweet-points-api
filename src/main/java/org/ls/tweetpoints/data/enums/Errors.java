package org.ls.tweetpoints.data.enums;

import lombok.Getter;

@Getter
public enum Errors {
    PHRASE_ALREADY_REGISTERED("100","Phrase already registered"),
    CAMPAIGN_IS_ALREADY_CURRENT("101","This campaign is already the current one"),
    CAMPAIGN_DOES_NOT_EXIST("102","Campaign does not exists"),
    NO_ACTIVE_CAMPAIGNS("103","There are no active campaigns"),
    NEW_CAMPAIGN_PHRASE_EXISTS("104","New campaign phrase already exists"),
    USER_NOT_FOUND("105","User not found"),
    TWEETED_CAMPAIGN_IS_NOT_CURRENT("106","The tweeted campaign is not the current one"),
    EMAIL_ALREADY_REGISTERED("107","E-mail already registered"),
    USER_DOES_NOT_HAVE_TWEETS("108","User does not have tweets"),
    EMAIL_ALREADY_PARTICIPATED("109","E-mail already has participated in this campaign"),
    EMAIL_INVALID("1001","E-mail is invalid"),
    EMPTY_OR_NULL_FIELD("1002", "%s cannot be null or empty");

    
    private final String code;
    private final String message;

    Errors(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getMessage(String fieldName) {
        if (this == EMPTY_OR_NULL_FIELD) {
            return String.format(message, fieldName);
        }
        return message;
    }
}
