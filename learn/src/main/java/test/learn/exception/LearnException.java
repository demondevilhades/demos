package test.learn.exception;

@SuppressWarnings("serial")
public class LearnException extends RuntimeException {

    public LearnException() {
        super();
    }

    public LearnException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public LearnException(String message, Throwable cause) {
        super(message, cause);
    }

    public LearnException(String message) {
        super(message);
    }

    public LearnException(Throwable cause) {
        super(cause);
    }
}
