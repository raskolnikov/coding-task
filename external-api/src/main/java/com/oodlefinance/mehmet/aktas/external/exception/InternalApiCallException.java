package com.oodlefinance.mehmet.aktas.external.exception;

import com.oodlefinance.mehmet.aktas.core.exception.component.ExceptionResponse;
import org.springframework.http.HttpStatus;

public class InternalApiCallException extends ExternalApiException{

	private final String exceptionResponse;
	private final String methodKey;
	private final HttpStatus httpStatus;

	public InternalApiCallException(String message, String exceptionResponse, String methodKey, HttpStatus httpStatus) {

		super(message);
		this.exceptionResponse = exceptionResponse;
		this.methodKey = methodKey;
		this.httpStatus = httpStatus;
	}


	public String getExceptionResponse() {
		return exceptionResponse;
	}

	public String getMethodKey() {
		return methodKey;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}
}
