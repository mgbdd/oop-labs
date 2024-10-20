package exceptions;

public class UnsupportedComandNameException extends Exception{
    public UnsupportedComandNameException() {
        super();
    }
    public UnsupportedComandNameException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return "wrong name of a command, check if it is in upper case and is in the list of commands";
    }
    @Override
    public String toString() {
        return getClass().getName() + ": " + getMessage();
    }
}
