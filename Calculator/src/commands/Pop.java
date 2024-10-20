package commands;

import main.ExecutionContext;

import java.util.EmptyStackException;

public class Pop implements Command{
    @Override
    public void execute(ExecutionContext context, String[] parts) {
        double res = 0;
        try {
            res = context.getStack().pop();
            context.getLogger().info(res + " was popped from the stack");
        } catch (EmptyStackException e) {
            context.getLogger().severe("attempt to pop from an empty stack in pop method");
            throw new EmptyStackException();
        }
        System.out.println(res);
    }

    public double getPopRes(ExecutionContext context)
    {
        return context.getStack().pop();
    }

}
