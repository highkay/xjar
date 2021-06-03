package io.xjar;

import org.apache.commons.cli.*;

public class Main {
    public static final void main(String args[]) {

        Options options = new Options();

        Option input = new Option("i", "input", true, "input file path");
        input.setRequired(true);
        options.addOption(input);

        Option output = new Option("o", "output", true, "output file path");
        output.setRequired(true);
        options.addOption(output);

        Option password = new Option("p", "password", true, "password");
        password.setRequired(true);
        options.addOption(password);

        Option include = new Option("n", "include", true, "include path in jar");
        include.setRequired(false);
        options.addOption(include);

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd = null;

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            e.printStackTrace();
            formatter.printHelp("encrypted java launcher", options);
            System.exit(1);
        }

        String inputFilePath = cmd.getOptionValue("input");
        String outputFilePath = cmd.getOptionValue("output");
        String passwordString = cmd.getOptionValue("password");
        String includePath = cmd.getOptionValue("include");

        try {
            XEncryption xEncryption = XCryptos.encryption()
                    .from(inputFilePath)
                    .use(passwordString)
                    .include("/**/*.class")
                    .include("/mapper/**/*Mapper.xml");
            if (includePath != null) {
                xEncryption = xEncryption.include(includePath);
            }
            xEncryption.exclude("/**/*.jar")
                    .exclude("/static/**/*")
                    .exclude("/META-INF/resources/**/*")
                    .to(outputFilePath);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
