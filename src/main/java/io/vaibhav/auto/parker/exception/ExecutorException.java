package io.vaibhav.auto.parker.exception;

public class ExecutorException extends Exception {
    private static final long serialVersionUID = -3552275262672621625L;

    private String errorCode = null;    // this will hold system defined error code

    public ExecutorException(String message, Throwable throwable) {
        super(message, throwable);
    }


    public ExecutorException(Throwable throwable) {
        super(throwable);
    }

    public ExecutorException(String errorCode, String message, Throwable throwable) {
        super(message, throwable);
        this.setErrorCode(errorCode);
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

}
