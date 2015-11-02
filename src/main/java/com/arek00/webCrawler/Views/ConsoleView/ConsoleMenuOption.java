package com.arek00.webCrawler.Views.ConsoleView;

public enum ConsoleMenuOption {

    UNKNOWN(-1),
    START_DOWNLOAD_OPTION(0),
    SET_DOMAIN(1),
    SET_DOWNLOAD_DIRECTORY(2),
    SET_ARTICLES_NUMBER(3),
    SET_QUEUE(4),
    SET_VISITED_LINKS(5),
    EXIT_OPTION(6);

    private final int value;

    private ConsoleMenuOption(int value) {
        this.value = value;
    }

    public static ConsoleMenuOption getOptionByValue(int value) {
        for (ConsoleMenuOption option : values()) {
            if (option.value == value) {
                return option;
            }
        }
        return UNKNOWN;
    }

}
