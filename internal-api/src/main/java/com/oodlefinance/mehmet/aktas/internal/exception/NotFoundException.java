package com.oodlefinance.mehmet.aktas.internal.exception;

/**
 * Created by Mehmet Aktas on 2021-05-19
 */


public class NotFoundException extends InternalApiException {

	private Long itemId;

	public NotFoundException(String message) {

		super(message);
	}

	public NotFoundException(String message, Long itemId) {

		this(message);
		this.itemId = itemId;
	}

}
