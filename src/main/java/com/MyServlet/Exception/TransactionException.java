package com.MyServlet.Exception;

public class TransactionException extends Exception {
    public TransactionException() {
        super();
    }

    public TransactionException(String message, Throwable cause) {
        super(message, cause);
    }

    public TransactionException(String message) {
        super(message);
    }
}