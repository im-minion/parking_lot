/**
 *
 */
package io.vaibhav.auto.parker.exception;

public class ExecutorException extends Exception {
    private static final long serialVersionUID = -3552275262672621625L;

    private String errorCode = null;    // this will hold system defined error code
    private Object[] errorParameters = null;    // this will hold parameters for error code/message

    public ExecutorException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public ExecutorException(String message) {
        super(message);
    }


    public ExecutorException(Throwable throwable) {
        super(throwable);
    }

    public ExecutorException(String errorCode, String message, Object[] errorParameters) {
        super(message);
        this.setErrorCode(errorCode);
        this.setErrorParameters(errorParameters);
    }

    public ExecutorException(String errorCode, String message, Throwable throwable) {
        super(message, throwable);
        this.setErrorCode(errorCode);
    }

    public ExecutorException(String errorCode, String message, Object[] errorParameters, Throwable throwable) {
        super(message, throwable);
        this.setErrorCode(errorCode);
        this.setErrorParameters(errorParameters);
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public Object[] getErrorParameters() {
        return errorParameters;
    }

    public void setErrorParameters(Object[] errorParameters) {
        this.errorParameters = errorParameters;
    }
}
