package exceptions;

public class RepeatingNumbersException extends Exception{
    public RepeatingNumbersException() {
        super();
    }

    @Override
    public String getMessage() {
        return "your number contains repeating number, each number has to be unique, try again";
    }

    @Override
    public String toString() {
        return getClass().getName() + ": " + getMessage();
    }
}
