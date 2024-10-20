package exceptions;

public class LettersInNumberException extends Exception {

    public LettersInNumberException() {
        super();
    }

    @Override
    public String getMessage() {
        return "your number contains letters while it shouldn't";
    }

    @Override
    public String toString() {
        return getClass().getName() + ": " + getMessage();
    }
}
