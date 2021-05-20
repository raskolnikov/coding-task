package com.oodlefinance.mehmet.aktas.external.exception.component;

import com.oodlefinance.mehmet.aktas.core.exception.component.ExceptionResponse;
import com.oodlefinance.mehmet.aktas.external.exception.InternalApiCallException;
import com.oodlefinance.mehmet.aktas.external.exception.InvalidRequestParamException;
import com.oodlefinance.mehmet.aktas.external.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mehmet Aktas on 2021-05-19
 * <p>
 * Exception handler class to return error message in response object
 */


@ControllerAdvice
public class ControllerExceptionHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(ControllerExceptionHandler.class);

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public @ResponseBody
	ExceptionResponse handleValidationExceptions(final MethodArgumentNotValidException ex,
			final HttpServletRequest request) {

		Map<String, String> errors = new HashMap<>();

		ex.getBindingResult().getAllErrors().forEach((error) -> {

			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();

			errors.put(fieldName, errorMessage);

		});

		ExceptionResponse exceptionResponse = new ExceptionResponse();

		exceptionResponse.setErrorMessage("Request validation failed");
		exceptionResponse.setFieldErrors(errors);
		exceptionResponse.setRequestedURI(request.getRequestURI());

		return exceptionResponse;

	}


	@ExceptionHandler(InvalidRequestParamException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public @ResponseBody
	ExceptionResponse handleInvalidRequestParamException(final InvalidRequestParamException ex,
			final HttpServletRequest request) {

		ExceptionResponse exceptionResponse = new ExceptionResponse();

		exceptionResponse.setErrorMessage(ex.getMessage());
		exceptionResponse.setRequestedURI(request.getRequestURI());

		LOGGER.error(ex.getMessage());

		return exceptionResponse;


	}


	@ExceptionHandler(NotFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public @ResponseBody
	ExceptionResponse handleNotFoundException(final NotFoundException ex, final HttpServletRequest request) {

		ExceptionResponse exceptionResponse = new ExceptionResponse();

		exceptionResponse.setErrorMessage(ex.getMessage());
		exceptionResponse.setRequestedURI(request.getRequestURI());

		LOGGER.error(ex.getMessage());

		return exceptionResponse;

	}

	@ExceptionHandler(InternalApiCallException.class)
	public ResponseEntity<Object> handleInternalApiCallException(final InternalApiCallException ex,
			final HttpServletRequest request) {

		LOGGER.error("Error occurred while calling internal api", ex);

		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

		return new ResponseEntity<>(ex.getExceptionResponse(),headers, ex.getHttpStatus());

	}


}
