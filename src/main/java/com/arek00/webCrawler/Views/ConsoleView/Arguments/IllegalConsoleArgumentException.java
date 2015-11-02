package com.arek00.webCrawler.Views.ConsoleView.Arguments;


import lombok.Getter;

public class IllegalConsoleArgumentException extends ConsoleArgumentException {
    @Getter private String message;

    public IllegalConsoleArgumentException(String message) {
        this.message = message;
    }
}
