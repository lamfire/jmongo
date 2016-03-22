package com.lamfire.jmongo.query;

public class ValidationException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ValidationException( String message ) {
            super(message);
    }

    public ValidationException( String message, Throwable cause ) {
            super(message, cause);
    }

}
