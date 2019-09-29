package io.vaibhav.auto.parker.parser;

import io.vaibhav.auto.parker.constants.Commands;
import io.vaibhav.auto.parker.executor.Executor;
import io.vaibhav.auto.parker.executor.IExecutor;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method; //just want to use invoke method

public class InputParser {

    private static final Commands commands = new Commands();

    private static IExecutor executor = new Executor();

    public void parseTextInput(String inputString) {
        // Split into commands and args of methods into String []
        String[] commandArray = inputString.split(" "); // now commandArray[0] will always be actual command
        Method method = commands.commandsMap.get(commandArray[0]); //get the method name from hash map
        if (null == method) { //see if its not null and then only proceed
            System.out.println("Invalid input");
            return;
        }
        try {
            switch (commandArray.length) {
                case 1://status
                    method.invoke(executor);
                    break;
                case 2://create, leave, getRegNoByColor, getSlotNoByColor, getSlotNoByRegNo
                    method.invoke(executor, commandArray[1]);
                    break;
                case 3: //park
                    method.invoke(executor, commandArray[1], commandArray[2]);
                    break;
                default:
                    System.out.println("Invalid input.");
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public void parseFileInput(String filePath) {
        // Assuming input to be a valid file path.
        File inputFile = new File(filePath);
        try {
            BufferedReader br = new BufferedReader(new FileReader(inputFile));
            String line;
            try {
                while ((line = br.readLine()) != null) {
                    parseTextInput(line.trim());
                }
            } catch (IOException ex) {
                System.out.println("Error in reading the input file. :(");
                ex.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found in the path specified. :(");
            e.printStackTrace();
        }
    }
}
