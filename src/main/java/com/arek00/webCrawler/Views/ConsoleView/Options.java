package com.arek00.webCrawler.Views.ConsoleView;

import java.util.Arrays;

public enum Options {

    UNKNOWN(-1),
    START_DOWNLOAD_OPTION(0),
    SET_DOWNLOAD_DIRECTORY(1),
    SET_QUEUE(2),
    SET_VISITED_LINKS(3),
    EXIT_OPTION(4);

    private final int value;

    private Options(int value) {
        this.value = value;
    }

    public static Options getOptionByValue(int value) {
        for (Options option : values()) {
            if (option.value == value) {
                return option;
            }
        }
        return UNKNOWN;
    }

}
