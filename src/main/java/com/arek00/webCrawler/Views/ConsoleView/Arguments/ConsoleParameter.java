package com.arek00.webCrawler.Views.ConsoleView.Arguments;


import lombok.Getter;

public enum ConsoleParameter {

    UNKNOWN("", false),
    SAVE_DIRECTORY("-D", true),
    DOMAIN("-d", true),
    MAX_ARTICLES("-a", false),
    QUEUE_FILE("-q", false),
    VISITED_LINKS("-v", false);

    @Getter private final String value;
    @Getter private final boolean mandatory;

    private ConsoleParameter(String value, boolean mandatory) {
        this.value = value;
        this.mandatory = mandatory;
    }

    public static ConsoleParameter getArgumentByPrefix(String prefix) {
        for (ConsoleParameter value : values()) {
            if (value.getValue().equals(prefix)) {
                return value;
            }
        }
        return UNKNOWN;
    }
}
