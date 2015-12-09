package test.learn.exception;

@SuppressWarnings("serial")
public class InitException extends LearnException {

    public InitException() {
        super();
    }

    public InitException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public InitException(String message, Throwable cause) {
        super(message, cause);
    }

    public InitException(String message) {
        super(message);
    }

    public InitException(Throwable cause) {
        super(cause);
    }
}
