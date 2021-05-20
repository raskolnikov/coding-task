package com.oodlefinance.mehmet.aktas.external.controller;

import com.oodlefinance.mehmet.aktas.core.request.NewMessageRequest;
import com.oodlefinance.mehmet.aktas.core.response.MessageDto;
import com.oodlefinance.mehmet.aktas.external.response.MessageCreatedDto;
import com.oodlefinance.mehmet.aktas.external.response.MessageDeletedDto;
import com.oodlefinance.mehmet.aktas.external.service.MessageFromInternalService;
import com.oodlefinance.mehmet.aktas.external.service.MessageService;
import com.oodlefinance.mehmet.aktas.external.util.MessageCreatedMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by Mehmet Aktas on 2021-05-19
 * <p>
 * This API Controller will be responsible for accepting message CRUD or related operations
 */

@RestController
@RequestMapping("/message")
public class MessageController {

	private static final Logger LOGGER = LoggerFactory.getLogger(MessageController.class);

	private final MessageService messageService;
	private final MessageCreatedMapper messageCreatedMapper;

	@Autowired
	public MessageController(MessageFromInternalService messageFromInternalService,
			MessageCreatedMapper messageCreatedMapper) {

		this.messageService = messageFromInternalService;
		this.messageCreatedMapper = messageCreatedMapper;
	}

	/**
	 * This API method will retrieve all message detail
	 * <p>
	 *
	 * @return all message details
	 */
	@GetMapping(value = "/", produces = "application/json")
	public List<MessageDto> getMessages() {

		return messageService.getMessages();

	}

	/**
	 * This API method will create new message
	 *
	 * @param newMessageRequest
	 * 		new message details will be in here
	 * @return created message detail
	 */
	@PostMapping(value = "/", consumes = "application/json", produces = "application/json")
	public MessageCreatedDto createMessage(@Valid @RequestBody NewMessageRequest newMessageRequest) {

		LOGGER.debug("New message creation request is received");

		MessageDto message = messageService.createMessage(newMessageRequest);

		LOGGER.debug("New message created id [{}]: ", message.getId());

		return messageCreatedMapper.convertToMessageCreatedDto(message);

	}

	/**
	 * This API method will retrieve message detail for given @param id
	 * <p>
	 * Account object will have  latest balance in it
	 *
	 * @param id
	 * 		message id
	 * @return message details
	 */
	@GetMapping(value = "/{id}", produces = "application/json")
	public MessageDto getMessage(@PathVariable("id") long id) {

		return messageService.getMessage(id);

	}

	/**
	 * This API method will delete message for given @param id
	 * <p>
	 *
	 * @param id
	 * 		message id
	 * @return message delete confirmation
	 */
	@DeleteMapping(value = "/{id}", produces = "application/json")
	public MessageDeletedDto deleteMessage(@PathVariable("id") long id) {

		String response = messageService.deleteMessage(id);

		return new MessageDeletedDto(id, response);

	}

}
