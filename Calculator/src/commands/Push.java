package commands;

import exceptions.UndefinedVariableException;
import main.ExecutionContext;

public class Push implements Command {
    @Override
    public void execute(ExecutionContext context, String[] parts) throws UndefinedVariableException {

        if (isNumber(parts[1])) {
            context.getStack().push(Double.parseDouble(parts[1]));
        }
        else if (context.getVariables().get(parts[1]) == null) {
            ExecutionContext.getLogger().severe("attempt to push the variable " + parts[1] + " which wasn't defined");
            throw new UndefinedVariableException();
        }
        else {
            double valueToPush = context.getVariables().get(parts[1]);
            context.getStack().push(valueToPush);
            ExecutionContext.getLogger().info(parts[1] + " = " + valueToPush + " was pushed to the stack");
        }

    }

    private boolean isNumber(String var) //проверяет является ли введенная строка записанным числом
    {
        for (int i = 0; i < var.length(); i++) {
            if ((int) var.charAt(i) < 48 || (int) var.charAt(i) > 57) return false;
        }
        return true;
    }

}
