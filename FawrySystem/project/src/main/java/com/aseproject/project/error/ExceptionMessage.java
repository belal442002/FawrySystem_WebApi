package com.aseproject.project.error;

public class ExceptionMessage extends Exception{
    public ExceptionMessage() {
        super();
    }

    public ExceptionMessage(String message) {
        super(message);
    }

    public ExceptionMessage(String message, Throwable cause) {
        super(message, cause);
    }

    public ExceptionMessage(Throwable cause) {
        super(cause);
    }

    protected ExceptionMessage(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
