package com.oodlefinance.mehmet.aktas.external.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@RequiredArgsConstructor
public class MessageDeletedDto {

	private final Long id;
	private final String message;

}
