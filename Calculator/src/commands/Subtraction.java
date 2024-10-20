package commands;

import main.ExecutionContext;

import java.util.EmptyStackException;

public class Subtraction implements Command{
    @Override
    public void execute(ExecutionContext context, String[] parts) {
        try {
            double arg1 = context.getStack().pop();
            double arg2 = context.getStack().pop();
            context.getStack().push(arg2 - arg1);
            context.getLogger().info(arg1 + " was subtracted from " + arg2 + ", the result " + (arg2 - arg1) + " was pushed to stack");
        } catch (EmptyStackException e) {
            context.getLogger().severe("attempt to pop from an empty stack in subtraction method");
            throw new EmptyStackException();
        }
    }
}
