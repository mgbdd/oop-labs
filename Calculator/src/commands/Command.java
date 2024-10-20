package commands;


import exceptions.UndefinedVariableException;
import exceptions.UnsupportedDefineNameException;
import main.ExecutionContext;

public interface Command {
    public void execute(ExecutionContext context, String[] parts) throws UnsupportedDefineNameException, UndefinedVariableException;
}
