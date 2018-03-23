package com.example.cli;

import com.example.controller.Controller;
import org.apache.commons.cli.*;

public class CliRunner {
    
    private Controller controller;

    public CliRunner(Controller controller) {
        this.controller = controller;
    }

    public void run(String[] args) {
        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine commandLine = parser.parse(getOptions(), args);
            String inputFilePath = commandLine.getOptionValue("i");
            String outputFilePath = commandLine.getOptionValue("o");
            controller.execute(inputFilePath, outputFilePath);
        } catch (ParseException e) {
            new HelpFormatter().printHelp("-i <path to input file> -o <path to save file>", "REVERSE POLISH NOTATION", getOptions(), e.getMessage());
        }
    }

    private Options getOptions() {
        Option inputOption = Option.builder("i")
                .longOpt("input")
                .required()
                .desc("path to input file (required)")
                .hasArg()
                .build();
        
        Option outputOption = Option.builder("o")
                .longOpt("output")
                .required()
                .desc("path to save file (required)")
                .hasArg()
                .build();
        
        Options options = new Options();
        options.addOption(inputOption);
        options.addOption(outputOption);
        return options;
    }
}
