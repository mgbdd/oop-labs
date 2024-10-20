package commands;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class CommandFactory {
    public static Command createCommand(String commandName) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Class<?> commandClass = Class.forName(commandName);
        return (Command) commandClass.newInstance();
    }
}
