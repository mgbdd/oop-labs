package commands;

import exceptions.ValueDefineException;
import exceptions.UnsupportedDefineNameException;
import main.ExecutionContext;

public class Define implements Command {
    @Override
    public void execute(ExecutionContext context, String[] parts) throws UnsupportedDefineNameException {
        if (isNumber(parts[1])) {
            ExecutionContext.getLogger().severe("attempt to define a value by another value");
            throw new ValueDefineException();
        } else if(!isSupportedName(parts[1])) {
            ExecutionContext.getLogger().severe("unsupported variable name is used in define class");
            throw new UnsupportedDefineNameException();
        }
        double value = Double.parseDouble(parts[2]);
        context.getVariables().put(parts[1], value);
        context.getLogger().info("variable " + parts[1] + " was defined with the value " + value);

    }

    private boolean isNumber(String var) {
        for (int i = 0; i < var.length(); i++) {
            //if ((int) var.charAt(i) < 48 || (int) var.charAt(i) > 57) return false;
            if(!Character.isDigit(var.charAt(i))) return false;
        }
        return true;
    }

    private boolean isSupportedName(String var) {
        int x = (int)var.charAt(0);
        if (Character.isDigit(var.charAt(0))) return false;
        if (!Character.isLetter(var.charAt(0))) return false;
        return true;
    }


}
