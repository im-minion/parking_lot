package io.vaibhav.auto.parker;

import io.vaibhav.auto.parker.parser.InputParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Application {
    public static void main(String[] args) {
        final InputParser inputParser = new InputParser();
        switch (args.length) {
            case 0:
                System.out.println("Please enter 'exit' to quit");
                System.out.println("Waiting for input...");
                while (true) {
                    try {
                        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
                        String inputString = bufferRead.readLine();
                        if (inputString.equalsIgnoreCase("exit")) {
                            break;
                        } else if (!inputString.isEmpty()) {
                            inputParser.parseTextInput(inputString.trim());
                        }
                    } catch (IOException e) {
                        System.out.println("Error in reading the input from console! Let me debug :)");
                        e.printStackTrace();
                    }
                }
                break;
            case 1:
                // File input/output TODO: verify
                inputParser.parseFileInput(args[0]);
                break;
            default:
                System.out.println("Invalid input. Usage: java -jar <jar_file_path> <input_file_path>");
        }
    }
}
