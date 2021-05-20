package com.oodlefinance.mehmet.aktas.internal.exception;

/**
 * Created by Mehmet Aktas on 2021-05-19
 */

public class InternalApiException extends RuntimeException {

	private static final long serialVersionUID = -7785949343623980782L;

	public InternalApiException() {
		super();
	}

	public InternalApiException(String message, Throwable cause) {
		super(message, cause);
	}

	public InternalApiException(String message) {
		super(message);
	}

	public InternalApiException(Throwable cause) {
		super(cause);
	}


}
