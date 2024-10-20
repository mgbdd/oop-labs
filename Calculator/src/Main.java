import commands.Command;
import commands.CommandFactory;
import exceptions.UndefinedVariableException;
import exceptions.UnsupportedComandNameException;
import exceptions.UnsupportedDefineNameException;
import exceptions.UnsupportedScanTypeException;
import main.ExecutionContext;

import java.io.FileNotFoundException;
import java.lang.reflect.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.EmptyStackException;
import java.util.Properties;


public class Main {
    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, IOException, UnsupportedScanTypeException {
        int numOfArg = args.length;
        ExecutionContext context;
        if (numOfArg == 1) {
            Class<?> x = ExecutionContext.class;
            Method method = x.getMethod("setFileName", String.class);
            method.invoke(null, args[0]);//устанавливаем имя файла с командами

            context = new ExecutionContext("file");
            ExecutionContext.getLogger().info("file scanner with file was created");
        } else if (numOfArg == 0) {
            context = new ExecutionContext("default");
            ExecutionContext.getLogger().info("default calculator was created");
        } else {
            ExecutionContext.getLogger().severe("wrong number of arguments");
            ExecutionContext.getFileHandler().close();
            return;
        }


        try {
            Properties properties = new Properties();
            FileInputStream input = new FileInputStream("commands/command.properties");
            properties.load(input);

            while (true) {
                String line = context.getScanner().nextLine();
                if (line.isEmpty() && context.getScanType().equalsIgnoreCase("default")) {
                    break;
                }

                String[] parts = line.split(" ");
                if (parts[0].equalsIgnoreCase("#")) parts[0] = "comment";
                String commandName = properties.getProperty(parts[0]);
                if (commandName == null) {
                    ExecutionContext.getLogger().severe("wrong command name was used");
                    throw new UnsupportedComandNameException();
                }

                //CommandFactory factory = new CommandFactory();
                Command command = CommandFactory.createCommand(commandName);

                command.execute(context, parts);

                if (context.getScanType().equalsIgnoreCase("file") && !context.getScanner().hasNextLine()) {
                    break;
                }
            }

        } catch (FileNotFoundException e) {
            ExecutionContext.getLogger().severe("configuration file with commands wasn't found");
        } catch (IOException e) {
            ExecutionContext.getLogger().severe("input-output error while attempting to load information from configuration file");
            ExecutionContext.getFileHandler().close();
        } catch (EmptyStackException | UnsupportedDefineNameException | ArithmeticException |
                 UndefinedVariableException | UnsupportedComandNameException e) {
            String error = e.toString();
            System.out.println(error);
        } finally {
            ExecutionContext.getFileHandler().close();
            context.getScanner().close();
        }


    }
}
