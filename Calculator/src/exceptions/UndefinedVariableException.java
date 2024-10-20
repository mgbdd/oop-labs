package exceptions;

public class UndefinedVariableException extends Exception{
    public UndefinedVariableException() {
        super();
    }
    public UndefinedVariableException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return "you tried to push a variable to the stack but it wasn't defined, you should define a variable first";
    }
    @Override
    public String toString() {
        return getClass().getName() + ": " + getMessage();
    }
}
