package exceptions;

public class UnsupportedDefineNameException extends Exception{
    public UnsupportedDefineNameException() {
        super();
    }

    public UnsupportedDefineNameException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return "you tried to defined a variable with an inappropriate name which starts with a symbol or with a digit\n the name of the variable has to start with a letter";
    }
    @Override
    public String toString() {
        return getClass().getName() + ": " + getMessage();
    }
}
