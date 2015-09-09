package com.arek00.webCrawler.Validators;

/**
 */
public class StringValidator {

    public static void isEmptyString(String string, String errorMessage) {
        if (string.isEmpty()) {
            throw new IllegalArgumentException(errorMessage);
        }
    }
}
