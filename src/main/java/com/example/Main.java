package com.example;

import com.example.cli.CliRunner;
import com.example.controller.JsonController;

public class Main {

    public static void main(String[] args) {
        new CliRunner(new JsonController()).run(args);
    }
}
