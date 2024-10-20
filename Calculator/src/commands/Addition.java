package commands;

import main.ExecutionContext;

import java.util.EmptyStackException;

public class Addition implements Command{
    @Override
    public void execute(ExecutionContext context, String[] parts) {
        try {
            double arg1 = context.getStack().pop();
            double arg2 = context.getStack().pop();
            context.getStack().push(arg1 + arg2);
            context.getLogger().info(arg1 + " was added to " + arg2 + ", the result " + (arg1 + arg2) + " was pushed to stack");
        } catch (EmptyStackException e) {
            context.getLogger().severe("attempt to pop from an empty stack in addition method");
            throw new EmptyStackException();
        }
    }
}
