package com.arek00.webCrawler.Validators;


public class ObjectValidator {

    public static void nullPointerValidate(Object... objects) {
        for (Object object : objects) {
            if (object == null) {
                throw new NullPointerException("Invalid argument. Object has no pointer.\n" + object.toString());
            }
        }
    }
}
