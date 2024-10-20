package exceptions;

public class ValueDefineException extends UnsupportedDefineNameException {
    public ValueDefineException() {
        super();
    }

    public ValueDefineException(String message) {
        super(message);
    }
    @Override
    public String getMessage() {
        return "you used a number instead of a defining name for the value, you should use a name that consists of letters";
    }
    @Override
    public String toString() {
        return getClass().getName() + ": " + getMessage();
    }
}
