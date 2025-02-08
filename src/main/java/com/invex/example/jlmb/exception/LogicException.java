package com.invex.example.jlmb.exception;

public class LogicException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public LogicException() {
		super();
	}

	public LogicException(String message) {
		super(message);
	}

	public LogicException(String message, Throwable cause) {
		super(message, cause);
	}

	public LogicException(Throwable cause) {
		super(cause);
	}
	
}
