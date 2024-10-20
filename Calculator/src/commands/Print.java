package commands;

import main.ExecutionContext;

import java.util.EmptyStackException;

public class Print implements Command{
    @Override
    public void execute(ExecutionContext context, String[] parts) {
        try{
            System.out.println(context.getStack().peek());
            context.getLogger().info(context.getStack().peek() + " was printed to the screen");
        }catch (EmptyStackException e)
        {
            ExecutionContext.getLogger().severe("attempt to peek from an empty stack in print class");
            throw new EmptyStackException();
        }

    }
}
