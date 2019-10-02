package test.fdr;

@SuppressWarnings("serial")
public class FaceDRException extends RuntimeException {

    public FaceDRException() {
        super();
    }

    public FaceDRException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public FaceDRException(String message, Throwable cause) {
        super(message, cause);
    }

    public FaceDRException(String message) {
        super(message);
    }

    public FaceDRException(Throwable cause) {
        super(cause);
    }
}
