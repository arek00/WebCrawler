package com.arek00.webCrawler.Views.ConsoleView;


import lombok.Getter;

public enum ConsoleArgumentsEnum {

    UNKNOWN(""),
    SAVE_DIRECTORY("-D"),
    DOMAIN("-d"),
    MAX_ARTICLES("-a"),
    QUEUE_FILE("-q"),
    VISITED_LINKS("-v");

    @Getter private final String value;

    private ConsoleArgumentsEnum(String value) {
        this.value = value;
    }


    public static ConsoleArgumentsEnum getArgumentByPrefix(String prefix) {
        for(ConsoleArgumentsEnum value : values()) {
            if(value.getValue().equals(prefix)) {
                return value;
            }
        }
        return UNKNOWN;
    }
}
