package exceptions;

public class UnsupportedScanTypeException extends Exception{
    public UnsupportedScanTypeException() {
        super();
    }

    public UnsupportedScanTypeException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return "you tried to set an unsupported scanner type: it can either be 'file' or 'default'";
    }
    @Override
    public String toString() {
        return getClass().getName() + ": " + getMessage();
    }
}
