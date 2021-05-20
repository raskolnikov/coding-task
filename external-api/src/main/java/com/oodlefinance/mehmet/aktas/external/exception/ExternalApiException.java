package com.oodlefinance.mehmet.aktas.external.exception;

/**
 * Created by Mehmet Aktas on 2021-05-19
 */

public class ExternalApiException extends RuntimeException {

	private static final long serialVersionUID = -7785949343623980782L;

	public ExternalApiException() {
		super();
	}

	public ExternalApiException(String message, Throwable cause) {
		super(message, cause);
	}

	public ExternalApiException(String message) {
		super(message);
	}

	public ExternalApiException(Throwable cause) {
		super(cause);
	}


}
