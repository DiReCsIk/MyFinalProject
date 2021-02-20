package com.MyServlet.Exception;

public class ConnectionException extends Exception{
    public ConnectionException() {
        super();
    }

    public ConnectionException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConnectionException(String message) {
        super(message);
    }
}
