package com.oodlefinance.mehmet.aktas.external.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oodlefinance.mehmet.aktas.core.exception.component.ExceptionResponse;
import com.oodlefinance.mehmet.aktas.external.exception.InternalApiCallException;
import feign.Response;
import feign.codec.Decoder;
import feign.codec.ErrorDecoder;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;

public class CustomErrorDecoder implements ErrorDecoder {

	@SneakyThrows
	@Override
	public Exception decode(String methodKey, Response response) {

		return new InternalApiCallException("Api call is failed",
				response.body().toString(),
				methodKey,
				HttpStatus.resolve(response.status()));

	}
}