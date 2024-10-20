package commands;

import main.ExecutionContext;

import java.util.EmptyStackException;

public class Sqrt implements Command{
    @Override
    public void execute(ExecutionContext context, String[] parts) {
        try {
            double var = context.getStack().pop();
            if(var < 0) throw new ArithmeticException();
            var = Math.sqrt(var);
            context.getStack().push(var);
            ExecutionContext.getLogger().info("the root was extracted from " + var * var + ", the result " + var + " pushed to the stack");
        } catch (EmptyStackException e) {
            ExecutionContext.getLogger().severe("attempt to pop from an empty stack in sqrt method");
        }
        catch (ArithmeticException e)
        {
            ExecutionContext.getLogger().severe("attempt to extract a root from negative value");
            throw new ArithmeticException();
        }

    }
}
