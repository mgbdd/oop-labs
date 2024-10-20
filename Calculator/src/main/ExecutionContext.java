package main;

import exceptions.UnsupportedScanTypeException;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class ExecutionContext{
    private static final Logger logger = Logger.getLogger(ExecutionContext.class.getName());
    private static FileHandler fileHandler;

     {
        try {
            fileHandler = new FileHandler("calculatorLogFile.log");
            logger.addHandler(fileHandler);
            fileHandler.setFormatter(new SimpleFormatter());
        } catch(IllegalArgumentException e)
        {
            logger.severe("unsupported argument for setFormatter or addHandler method");
            throw new IllegalAccessException();
        }
        catch(SecurityException e)
        {
            logger.severe("not enough rights of access to log file");
            throw  new SecurityException();
        }
        catch(IOException e)
        {
            logger.severe("input-output error while working with log file");
            throw e;
        }
    }

    private final Scanner scanner;
    private final Stack<Double> numbers = new Stack<>(); //для хранения чисел
    private final Map<String, Double> variables = new HashMap<>(); //для хранения переменных и их значений

    private static String fileName;
    private final String scanType;

    public ExecutionContext(String type) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IOException, UnsupportedScanTypeException {
        if(type.equalsIgnoreCase("file"))
        {
            File file = new File(fileName);
            scanner = new Scanner(file);
        }
        else if(type.equalsIgnoreCase("default"))
        {
            scanner = new Scanner(System.in);
        }
        else{
            logger.severe("unsupported scanner type");
            throw new UnsupportedScanTypeException();
        }
        scanType = type;
    }

    public Scanner getScanner()
    {
        return scanner;
    }

    public static Logger getLogger()
    {
        return logger;
    }

    public static FileHandler getFileHandler()
    {
        return fileHandler;
    }

    public Stack<Double> getStack()
    {
        return numbers;
    }

    public Map<String, Double> getVariables()
    {
        return variables;
    }

    public static void setFileName(String name)
    {
        fileName = name;
    }

    public String getScanType()
    {
        return scanType;
    }

}


/*
геттеры:
    стек:
      - peek
      - push
      - pop
      - isEmpty
    логгеры:
    - info
    - severe
    мапа:
    - get
    - put
    - containsKey
*/

