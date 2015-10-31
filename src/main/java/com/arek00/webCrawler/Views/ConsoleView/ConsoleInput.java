package com.arek00.webCrawler.Views.ConsoleView;


import java.util.Scanner;

public class ConsoleInput {

    private Scanner scanner;


    public ConsoleInput() {
        scanner = new Scanner(System.in);
    }

    public String getAction() {
        return scanner.nextLine();
    }

}
