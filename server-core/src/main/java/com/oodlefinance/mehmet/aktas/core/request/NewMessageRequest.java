package com.oodlefinance.mehmet.aktas.core.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

/**
 * Created by Mehmet Aktas on 2021-05-19
 */

@Getter
@Setter
public class NewMessageRequest {

	@NotEmpty(message = "Message is mandatory")
	private String message;

}
