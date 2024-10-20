package exceptions;

public class InvalidLengthException extends Exception{
    public InvalidLengthException() {
        super();
    }

    @Override
    public String getMessage() {
        return "your number has to be four-digit, try again";
    }
    @Override
    public String toString() {
        return getClass().getName() + ": " + getMessage();
    }
}
