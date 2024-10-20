package commands;

import main.ExecutionContext;

import java.util.EmptyStackException;

public class Division implements Command {
    @Override
    public void execute(ExecutionContext context, String[] parts) {
        try {
            double arg1 = context.getStack().pop();
            if (arg1 == 0) {
                context.getLogger().severe("error:division by zero");
                context.getFileHandler().close();
                throw new ArithmeticException();
            }
            double arg2 = context.getStack().pop();
            double res;
            res = arg2 / arg1;
            context.getStack().push(res);
            context.getLogger().info(arg2 + " was divided by " + arg1 + ", the result " + (arg2 / arg1) + " was pushed to stack");
        } catch (EmptyStackException e) {
            context.getLogger().severe("attempt to pop from an empty stack in division method");
            throw new EmptyStackException();
        }
    }
}
