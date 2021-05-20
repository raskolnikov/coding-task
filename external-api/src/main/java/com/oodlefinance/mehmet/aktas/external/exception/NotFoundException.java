package com.oodlefinance.mehmet.aktas.external.exception;

/**
 * Created by Mehmet Aktas on 2021-05-19
 */


public class NotFoundException extends ExternalApiException {

	private Long itemId;

	public NotFoundException(String message) {

		super(message);
	}

	public NotFoundException(String message, Long itemId) {

		this(message);
		this.itemId = itemId;
	}

}
